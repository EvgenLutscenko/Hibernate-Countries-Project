package com.javarush.ua.lutscenko.repository;

import com.javarush.ua.lutscenko.entity.City;

import java.util.List;

public interface CityRepo {
    List<City> getItems(int offset, int limit);
    int getTotalCount();
    City getById(Integer id);
}
