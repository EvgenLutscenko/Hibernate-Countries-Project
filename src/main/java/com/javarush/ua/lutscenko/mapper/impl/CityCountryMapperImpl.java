package com.javarush.ua.lutscenko.mapper.impl;

import com.javarush.ua.lutscenko.entity.City;
import com.javarush.ua.lutscenko.entity.Country;
import com.javarush.ua.lutscenko.entity.CountryLanguage;
import com.javarush.ua.lutscenko.mapper.CityCountryMapper;
import com.javarush.ua.lutscenko.redis.CityCountry;
import com.javarush.ua.lutscenko.redis.Language;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CityCountryMapperImpl implements CityCountryMapper {
    @Override
    public List<CityCountry> transformData(List<City> cities) {
        return cities.stream().map(city -> {
            CityCountry res = new CityCountry();
            res.setId(city.getId());
            res.setName(city.getName());
            res.setPopulation(city.getPopulation());
            res.setDistrict(city.getDistrict());

            Country country = city.getCountry();
            res.setAlternativeCountryCode(country.getAlternativeCode());
            res.setContinent(country.getContinent());
            res.setCountryCode(country.getCode());
            res.setCountryName(country.getName());
            res.setCountryPopulation(country.getPopulation());
            res.setCountryRegion(country.getRegion());
            res.setCountrySurfaceArea(country.getSurfaceArea());
            Set<CountryLanguage> countryLanguages = country.getLanguages();
            Set<Language> languages = countryLanguages.stream().map(cl -> {
                Language language = new Language();
                language.setLanguage(cl.getLanguage());
                language.setIsOfficial(cl.getIsOfficial());
                language.setPercentage(cl.getPercentage());
                return language;
            }).collect(Collectors.toSet());
            res.setLanguages(languages);

            return res;
        }).collect(Collectors.toList());
    }
}
