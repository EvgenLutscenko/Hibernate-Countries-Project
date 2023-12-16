package com.javarush.ua.lutscenko.redis;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Language {
    private String language;
    private Boolean isOfficial;
    private BigDecimal percentage;


    //Getters and Setters omitted
}
