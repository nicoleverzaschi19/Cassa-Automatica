package com.java.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ScontrinoDto {
    private LocalDate dataCreazione;
    private List<ArticoloDto> articoli;
    private double totale;
}
