package com.java.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class RepartoPrezzoDto {
        private Double sommaPrezzo;
        private String reparto;
}
