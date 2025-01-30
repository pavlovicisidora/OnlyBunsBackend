package com.ISA.OnlyBunsBackend.service.impl;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterServiceImpl {

    private static final int MAX_REQUESTS = 5; // Maksimalno 5 zahteva
    private static final long TIME_WINDOW = 60 * 1000; // 1 minut u milisekundama

    private final Map<Integer, Queue<Long>> requestCounts = new ConcurrentHashMap<>();

    public boolean isAllowed(Integer userId){
        long now = System.currentTimeMillis();

        requestCounts.putIfAbsent(userId, new LinkedList<>());

        Queue<Long> timestamps = requestCounts.get(userId);

        while (!timestamps.isEmpty() && timestamps.peek() < now - TIME_WINDOW) {
            timestamps.poll();
        }
        if (timestamps.size() < MAX_REQUESTS) {
            timestamps.add(now); // Dodaj novi zahtev
            return true; // Dozvoli zahtev
        } else {
            return false; // Odbij zahtev (limit premašen)
        }
    }
}
