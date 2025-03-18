package com.java.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Articolo {
    //Articolo: nome articolo, grammatura (intero), unità di misura (kg, g, pz), reparto
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nomeArticolo;

    private int grammatura;

    @Enumerated(EnumType.STRING)
    private UnitaDiMisura unitaDiMisura;

    private String reparto;

    @OneToMany(mappedBy = "articolo", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Barcode> barcodes = new ArrayList<>();

    @OneToMany(mappedBy = "articolo", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Prezzo> prezzi;

    @OneToMany(mappedBy = "articolo", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Stock> stock;

    @ManyToMany(mappedBy = "articoli", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Scontrino> scontrini;


    public double getPrezzoAttuale() {
        LocalDate oggi = LocalDate.now();
        for (Prezzo prezzo : prezzi) {
            if (oggi.isAfter(prezzo.getDataInizio()) && (prezzo.getDataFine() == null || oggi.isBefore(prezzo.getDataFine()))) {
                return prezzo.getPrezzo();
            }
        }
        return 0.0;
    }

    public void decrementaStock(){
        LocalDate oggi = LocalDate.now();
        for(Stock s : this.stock) {
            if ((s.getData() == oggi) && s != null && s.getQuantita() > 0) {
                s.setQuantita(s.getQuantita() - 1);
            } else if (s.getQuantita() == 0) {
                System.out.println("Stock non sufficiente");
            }
        }
    }

    public enum UnitaDiMisura{
        KG,G,PZ
    }
}
