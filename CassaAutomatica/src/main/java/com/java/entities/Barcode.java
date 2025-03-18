package com.java.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;


@Entity
@Data
public class Barcode {
    // Barcode: codice barcode associato all'articolo. Deve prevedere l'inizio e fine validità. Un articolo può essere associato a più barcode

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String codice;
    private LocalDate inizioValidita;
    private LocalDate fineValidita;
    @ManyToOne
    @JoinColumn(name = "articolo_id", nullable = false)
    private Articolo articolo = new Articolo();
}
