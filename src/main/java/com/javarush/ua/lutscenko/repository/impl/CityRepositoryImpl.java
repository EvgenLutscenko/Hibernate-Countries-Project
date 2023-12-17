package com.javarush.ua.lutscenko.repository.impl;

import com.javarush.ua.lutscenko.entity.City;
import com.javarush.ua.lutscenko.repository.CityRepo;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class CityRepositoryImpl implements CityRepo {
    private final SessionFactory sessionFactory;

    public CityRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<City> getItems(int offset, int limit) {
        Query<City> query = sessionFactory.getCurrentSession().createQuery("select c from City c", City.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.list();
    }

    public int getTotalCount() {
        Query<Long> query = sessionFactory.getCurrentSession().createQuery("select count(c) from City c", Long.class);
        return Math.toIntExact(query.uniqueResult());
    }

    public City getById(Integer id) {
        Query<City> query = sessionFactory.getCurrentSession().createQuery("select c from City c join fetch c.country where c.id = :ID", City.class);
        query.setParameter("ID", id);
        return query.getSingleResult();
    }
}
