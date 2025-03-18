package com.java.entities;

import com.java.dtos.ArticoloDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Scontrino {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToMany
    @JoinTable(
            name = "scontrino_articolo",
            joinColumns = @JoinColumn(name = "scontrino_id"),
            inverseJoinColumns = @JoinColumn(name = "articolo_id")
    )
    private List<Articolo> articoli = new ArrayList<>();
    private double totale;
    private LocalDate data;

    public Scontrino(List<Articolo> articoli, double totale, LocalDate now) {
        this.articoli = articoli;
        this.totale = totale;
        this.data = now;
    }
}
