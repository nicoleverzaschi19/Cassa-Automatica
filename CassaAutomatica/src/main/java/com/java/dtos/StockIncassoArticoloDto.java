package com.java.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockIncassoArticoloDto {
    private Double incasso;
    private Long stock;
    private Integer articolo;
}
