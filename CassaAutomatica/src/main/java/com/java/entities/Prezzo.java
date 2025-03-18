package com.java.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Prezzo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private double prezzo;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    @ManyToOne
    @JoinColumn(name = "articolo_id")
    private Articolo articolo;
}
