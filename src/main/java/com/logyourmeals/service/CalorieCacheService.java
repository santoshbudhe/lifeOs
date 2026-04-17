package com.logyourmeals.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CalorieCacheService {

    private final Map<String, Integer> cache = new ConcurrentHashMap<>();

    public Integer get(String query) {
        return cache.get(query.toLowerCase());
    }

    public void put(String query, Integer calories) {
        cache.put(query.toLowerCase(), calories);
    }
}
