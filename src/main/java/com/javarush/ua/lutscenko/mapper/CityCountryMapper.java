package com.javarush.ua.lutscenko.mapper;

import com.javarush.ua.lutscenko.entity.City;
import com.javarush.ua.lutscenko.redis.CityCountry;

import java.util.List;

public interface CityCountryMapper {
    List<CityCountry> transformData(List<City> cities);
}
