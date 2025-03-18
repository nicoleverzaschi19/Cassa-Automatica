package com.java.managers;

import com.java.dtos.*;
import com.java.daos.ArticoloDao;
import com.java.daos.BarcodeDao;
import com.java.daos.PrezzoDao;
import com.java.daos.ScontrinoDao;
import com.java.entities.Articolo;
import com.java.entities.Barcode;
import com.java.entities.Prezzo;
import com.java.entities.Scontrino;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CassaAutomaticaService {
    @Autowired
    private ArticoloDao articoloDao;
    @Autowired
    private BarcodeDao barcodeDao;
    @Autowired
    private PrezzoDao prezzoDao;
    @Autowired
    ScontrinoDao scontrinoDao;

    public CassaAutomaticaService() {
    }

    public void addArticolo(Articolo articolo){
        if (articolo.getBarcodes() != null) {
            for (Barcode barcode : articolo.getBarcodes()) {
                barcode.setArticolo(articolo);
            }
        }
        if (articolo.getPrezzi() != null) {
            for (Prezzo prezzo : articolo.getPrezzi()) {
                prezzo.setArticolo(articolo);
            }
        }
        articoloDao.save(articolo);
    }

    public Scontrino creaScontrino(){
        Scontrino s = new Scontrino(null, 0, LocalDate.now());
        scontrinoDao.save(s);
        return s;
    }
    public ScontrinoDto creaScontrinoDaArticoli(List<String> codiciArticoli) {
        return creaScontrinoDaArticoli(codiciArticoli,null);
    }

    @Transactional
    public ScontrinoDto creaScontrinoDaArticoli(List<String> codiciArticoli,Integer idScontrino) {
        List<Articolo> articoli = new ArrayList<>();
        List<ArticoloDto> articoloDtos = new ArrayList<>();
        Scontrino scontrino;
        if (Objects.isNull(idScontrino)) {
            scontrino = new Scontrino(articoli,0.0, LocalDate.now());
        } else {
            scontrino = scontrinoDao.findById(idScontrino).orElseThrow(() -> new RuntimeException("Scontrino non trovato"));
        }
        AtomicReference<Double> totale = new AtomicReference<>(scontrino.getTotale());

        codiciArticoli.forEach(c -> articoli.add(barcodeDao.findArticoloByCodice(c).orElseThrow()));
        articoli.forEach(a -> {
            Optional<ArticoloDto> existingDtoOpt = articoloDtos.stream()
                    .filter(dto -> dto.getNomeArticolo().equals(a.getNomeArticolo()))
                    .findFirst();
            if (existingDtoOpt.isEmpty()) {
                ArticoloDto dto = new ArticoloDto();
                dto.setNomeArticolo(a.getNomeArticolo());
                dto.setGrammatura(String.valueOf(a.getGrammatura()));
                dto.setReparto(a.getReparto());
                dto.setUnitaDiMisura(String.valueOf(a.getUnitaDiMisura()));
                dto.setQuantita(1);
                articoloDtos.add(dto);
            }
            else{
                ArticoloDto existingDto = existingDtoOpt.get();
                existingDto.setQuantita(existingDto.getQuantita() + 1);
            }
            totale.updateAndGet(v -> v + a.getPrezzoAttuale());
            //a.decrementaStock();
        });

        scontrino.setTotale(totale.get());
        scontrino.setArticoli(articoli);

        scontrinoDao.save(scontrino);
        return new ScontrinoDto(LocalDate.now(),articoloDtos,totale.get());
    }


    public double incassoGiornaliero(){
        Optional<Double> totaleOpt = scontrinoDao.findTotaleGiornaliero();
        return totaleOpt.isEmpty() ? 0 : totaleOpt.get();
    }

    public Object incassoGiornalieroPerReparto(){
        List<RepartoPrezzoDto> repartoPrezzoDto = scontrinoDao.findTotaleGiornalieroByReparto();
        return repartoPrezzoDto.isEmpty() ? "Nessun incasso giornaliero." : repartoPrezzoDto;
    }
    public Object incassoAnnuoPerReparto(){
        List<RepartoPrezzoDto> totaleAnnuoPerReparto = scontrinoDao.findTotaleAnnuoByReparto();
        return totaleAnnuoPerReparto.isEmpty() ? "Totale annuo assente." : totaleAnnuoPerReparto;
    }
    public Object getStockVendutoAndIncassoGiornata(){
        List<StockIncassoArticoloDto> stockAndIncassoGiornaliero = scontrinoDao.findStockAndIncassoGiornaliero();
        return stockAndIncassoGiornaliero.isEmpty() ? "Stock e incasso giornaliero non presente." : stockAndIncassoGiornaliero;
    }
    public Object getStockGiornaliero(){
        List<StockArticoloDto> stockGiornaliero = scontrinoDao.findStockGiornaliero();
        return stockGiornaliero.isEmpty() ? "Stock giornaliero non disponibile." : stockGiornaliero;
    }
}
