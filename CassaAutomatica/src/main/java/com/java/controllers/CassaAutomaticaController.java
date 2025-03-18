package com.java.controllers;

import com.java.daos.ArticoloDao;
import com.java.daos.BarcodeDao;
import com.java.daos.PrezzoDao;
import com.java.entities.Articolo;
import com.java.entities.Barcode;
import com.java.entities.Prezzo;
import com.java.entities.Scontrino;
import com.java.managers.CassaAutomaticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/articolo/")
public class CassaAutomaticaController {
    @Autowired
    private CassaAutomaticaService cassaAutomaticaService;

    @PostMapping("add/articolo")
    public ResponseEntity<?> addarticolo(@RequestBody Articolo articolo) {
        cassaAutomaticaService.addArticolo(articolo);
        return ResponseEntity.ok("Articolo aggiunto correttamente.");
    }
    @PostMapping("/create/barcode")
    public ResponseEntity<?> createBarcode() {
        // Creare un endpoint che permetta di creare uno scontrino
        return ResponseEntity.ok(cassaAutomaticaService.creaScontrino());
    }
    @PostMapping("/create/barcode/fromList")
    public ResponseEntity<?> createBarcode(@RequestBody List<String> barcodes) {
        // Creare un endpoint che permetta di creare uno scontrino
        /*
        ["123455667",
        "123455667",
        "234253635"]
         */
        return ResponseEntity.ok(cassaAutomaticaService.creaScontrinoDaArticoli(barcodes));
    }
    @PostMapping("/add/articolo/barcode/{barcode}/{idScontrino}")
    public ResponseEntity<?> addArticolo(@PathVariable String barcode,@PathVariable Integer idScontrino) {
        // Dato un barcode, registrare un articolo all'interno dello scontrino. è possibile inserire lo stesso articolo più volte
        return ResponseEntity.ok(cassaAutomaticaService.creaScontrinoDaArticoli(List.of(barcode),idScontrino));
    }

    @GetMapping("/incasso/giornata")
    public ResponseEntity<?> getIncassoGiornata() {
       return ResponseEntity.ok("Totale giornaliero incassato: " + cassaAutomaticaService.incassoGiornaliero() + "€");
    }

    @GetMapping("/get/incasso/reparto/giornata")
    public ResponseEntity<?> getIncassoGiornataPerReparto() {
        // Rendere disponibile un endpoint che calcoli l'incasso per reparto, data una giornata
        return ResponseEntity.ok(cassaAutomaticaService.incassoGiornalieroPerReparto());

    }
    @GetMapping("/get/incasso/reparto/annuo")
    public ResponseEntity<?> getIncassoAnnuoPerReparto() {
        // Rendere disponibile un endpoint che calcoli l'incasso per reparto, dato un anno
        return ResponseEntity.ok(cassaAutomaticaService.incassoAnnuoPerReparto());
    }

    @GetMapping("/get/incasso/stock")
    public ResponseEntity<?> getStockVendutoAndIncassoGiornata() {
        // Rendere disponibile un endpoint che calcoli, per ogni articolo, i pezzi venduti e l'incasso dell'articolo, data una giornata.
        return ResponseEntity.ok(cassaAutomaticaService.getStockVendutoAndIncassoGiornata());
    }

    @GetMapping("/stock/giornata")
    public ResponseEntity<?> getStockGiornata() {
        // Dati gli scontrini, calcolare lo stock a fine giornata
        return ResponseEntity.ok(cassaAutomaticaService.getStockGiornaliero());

    }
}
