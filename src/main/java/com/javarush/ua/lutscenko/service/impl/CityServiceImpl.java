package com.javarush.ua.lutscenko.service.impl;

import com.javarush.ua.lutscenko.entity.CountryLanguage;
import com.javarush.ua.lutscenko.repository.impl.CityRepositoryImpl;
import com.javarush.ua.lutscenko.entity.City;
import com.javarush.ua.lutscenko.service.CityService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CityServiceImpl implements CityService {
    private final CityRepositoryImpl cityRepo;
    private final SessionFactory sessionFactory;

    public CityServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.cityRepo = new CityRepositoryImpl(sessionFactory);
    }

    @Override
    public List<City> fetchData() {
        try (Session session = sessionFactory.getCurrentSession()) {
            List<City> allCities = new ArrayList<>();
            session.beginTransaction();

            int totalCount = cityRepo.getTotalCount();
            int step = 500;
            for (int i = 0; i < totalCount; i += step) {
                allCities.addAll(cityRepo.getItems(i, step));
            }
            session.getTransaction().commit();
            return allCities;
        }
    }

    @Override
    public City getById(Integer id) {
        return cityRepo.getById(id);
    }
}
