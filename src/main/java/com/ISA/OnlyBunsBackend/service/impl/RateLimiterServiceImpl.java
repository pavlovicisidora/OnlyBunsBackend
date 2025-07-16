package com.ISA.OnlyBunsBackend.service.impl;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterServiceImpl {

    private static final int MAX_REQUESTS = 5; // Maksimalno 5 zahteva, inace treba 60 za komentar
    private static final long TIME_WINDOW = 60 * 1000; // 1 minut u milisekundama, inace treba u sat vremena za komentar za 3.10

    private final Map<Integer, Queue<Long>> requestCounts = new ConcurrentHashMap<>();

    //RateLimiter
    public boolean isAllowed(Integer userId){
        long now = System.currentTimeMillis();

        requestCounts.putIfAbsent(userId, new LinkedList<>());   //Ako nije imao nijedan zahtev

        Queue<Long> timestamps = requestCounts.get(userId);   //Sva prethodna vremena kada je slao zahteve

        while (!timestamps.isEmpty() && timestamps.peek() < now - TIME_WINDOW) {
            timestamps.poll();    //Uklanjanje vremena koja su van jednog minuta
        }
        if (timestamps.size() < MAX_REQUESTS) {
            timestamps.add(now); // Dodaj novi zahtev
            return true; // Dozvoli zahtev
        } else {
            return false; // Odbij zahtev (limit premašen)
        }
    }
}
