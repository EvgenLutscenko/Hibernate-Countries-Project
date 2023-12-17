package com.javarush.ua.lutscenko;

import com.javarush.ua.lutscenko.entity.City;
import com.javarush.ua.lutscenko.mapper.CityCountryMapper;
import com.javarush.ua.lutscenko.mapper.impl.CityCountryMapperImpl;
import com.javarush.ua.lutscenko.redis.CityCountry;
import com.javarush.ua.lutscenko.service.CityService;
import com.javarush.ua.lutscenko.service.CountryService;
import com.javarush.ua.lutscenko.service.impl.CityServiceImpl;
import com.javarush.ua.lutscenko.service.impl.CountryServiceImpl;
import com.javarush.ua.lutscenko.util.BDUtil;
import com.javarush.ua.lutscenko.util.RedisUtil;

import java.util.List;

/**
 * Hello world!
 *
 */
public class Main
{
    private final CityService cityService;
    private final CountryService countryService;

    private final CityCountryMapper cityCountryMapper;

    public Main() {
        cityService = new CityServiceImpl(BDUtil.getSessionFactory());
        countryService = new CountryServiceImpl(BDUtil.getSessionFactory());

        cityCountryMapper = new CityCountryMapperImpl();
    }

    public static void main(String[] args )
    {
        Main main = new Main();
        List<City> allCities = main.cityService.fetchData();
        List<CityCountry> preparedData = main.cityCountryMapper.transformData(allCities);
        RedisUtil.pushToRedis(preparedData);

        //закроем текущую сессию, чтоб точно делать запрос к БД, а не вытянуть данные из кэша
        BDUtil.getSessionFactory().getCurrentSession().close();

        //выбираем случайных 10 id городов
        //так как мы не делали обработку невалидных ситуаций, используй существующие в БД id
        List<Integer> ids = List.of(3, 2545, 123, 4, 189, 89, 3458, 1189, 10, 102);

        long startRedis = System.currentTimeMillis();
        RedisUtil.testRedisData(ids);
        long stopRedis = System.currentTimeMillis();

        long startMysql = System.currentTimeMillis();
        BDUtil.testMysqlData(ids, main.cityService);
        long stopMysql = System.currentTimeMillis();

        System.out.printf("%s:\t%d ms\n", "Redis", (stopRedis - startRedis));
        System.out.printf("%s:\t%d ms\n", "MySQL", (stopMysql - startMysql));

        main.shutdown();
    }

    private void shutdown() {
        BDUtil.shutdown();
        RedisUtil.shutdown();
    }
}
