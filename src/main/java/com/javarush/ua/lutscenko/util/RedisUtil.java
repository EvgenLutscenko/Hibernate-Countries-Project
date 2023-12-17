package com.javarush.ua.lutscenko.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.ua.lutscenko.redis.CityCountry;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisStringCommands;

import java.util.List;

import static java.util.Objects.nonNull;

public class RedisUtil {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final RedisClient redisClient = prepareRedisClient();

    private static final String host = "localhost";
    private static final String bridgeHostForDocker = "172.17.0.1";

    private static final int port = 6379;
    private static RedisClient prepareRedisClient() {
        RedisClient redisClient = RedisClient.create(RedisURI.create(host, port));
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            System.out.println("\nConnected to Redis\n");
        }
        return redisClient;
    }

    public static void pushToRedis(List<CityCountry> data) {
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisStringCommands<String, String> sync = connection.sync();
            for (CityCountry cityCountry : data) {
                try {
                    sync.set(String.valueOf(cityCountry.getId()), mapper.writeValueAsString(cityCountry));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void testRedisData(List<Integer> ids) {
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisStringCommands<String, String> sync = connection.sync();
            for (Integer id : ids) {
                String value = sync.get(String.valueOf(id));
                try {
                    mapper.readValue(value, CityCountry.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void shutdown(){
        if (nonNull(redisClient)) {
            redisClient.shutdown();
        }
    }

}
