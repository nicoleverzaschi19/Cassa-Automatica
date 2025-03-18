package com.java.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticoloDto {
    private String nomeArticolo;
    private String grammatura;
    private String reparto;
    private String unitaDiMisura;
    private int quantita;
}
