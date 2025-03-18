package com.java.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private LocalDate data;
    private int quantita;
    @ManyToOne
    @JoinColumn(name = "articolo_id", nullable = false)
    private Articolo articolo;
}
