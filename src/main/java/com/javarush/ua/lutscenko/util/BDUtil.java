package com.javarush.ua.lutscenko.util;

import com.javarush.ua.lutscenko.entity.City;
import com.javarush.ua.lutscenko.entity.Country;
import com.javarush.ua.lutscenko.entity.CountryLanguage;
import com.javarush.ua.lutscenko.service.CityService;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Set;

import static java.util.Objects.nonNull;

public class BDUtil {
    @Getter
    private static final SessionFactory sessionFactory = prepareRelationalDb();
    public static SessionFactory prepareRelationalDb() {
        final SessionFactory sessionFactory;

        sessionFactory = new Configuration()
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(CountryLanguage.class)
                .buildSessionFactory();
        return sessionFactory;
    }

    public static void testMysqlData(List<Integer> ids, CityService cityService) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            for (Integer id : ids) {
                City city = cityService.getById(id);
                Set<CountryLanguage> languages = city.getCountry().getLanguages();
            }
            session.getTransaction().commit();
        }
    }

    public static void shutdown(){
        if (nonNull(sessionFactory)) {
            sessionFactory.close();
        }
    }
}
