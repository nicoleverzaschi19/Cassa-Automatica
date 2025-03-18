package com.java.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockArticoloDto {
    private Double stock;
    private Integer articolo;
}
