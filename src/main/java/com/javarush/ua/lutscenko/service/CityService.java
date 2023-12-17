package com.javarush.ua.lutscenko.service;

import com.javarush.ua.lutscenko.entity.City;

import java.util.List;

public interface CityService {
    List<City> fetchData();

    City getById(Integer id);
}
