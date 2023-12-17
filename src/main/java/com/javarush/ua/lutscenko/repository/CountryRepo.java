package com.javarush.ua.lutscenko.repository;

import com.javarush.ua.lutscenko.entity.Country;

import java.util.List;

public interface CountryRepo {
    List<Country> getAll();
}
