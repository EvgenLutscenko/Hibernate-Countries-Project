package com.javarush.ua.lutscenko.repository.impl;

import com.javarush.ua.lutscenko.entity.Country;
import com.javarush.ua.lutscenko.repository.CountryRepo;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class CountryRepositoryImpl implements CountryRepo {
    private final SessionFactory sessionFactory;

    public CountryRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Country> getAll() {
        Query<Country> query = sessionFactory.getCurrentSession().createQuery("select c from Country c join fetch c.languages", Country.class);
        return query.list();
    }
}
