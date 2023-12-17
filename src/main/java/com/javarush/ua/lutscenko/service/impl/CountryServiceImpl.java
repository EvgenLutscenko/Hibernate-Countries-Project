package com.javarush.ua.lutscenko.service.impl;

import com.javarush.ua.lutscenko.repository.CountryRepo;
import com.javarush.ua.lutscenko.repository.impl.CountryRepositoryImpl;
import com.javarush.ua.lutscenko.service.CountryService;
import org.hibernate.SessionFactory;

public class CountryServiceImpl implements CountryService {
    private final CountryRepo countryRepo;
    private final SessionFactory sessionFactory;
    public CountryServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.countryRepo = new CountryRepositoryImpl(sessionFactory);
    }
}
