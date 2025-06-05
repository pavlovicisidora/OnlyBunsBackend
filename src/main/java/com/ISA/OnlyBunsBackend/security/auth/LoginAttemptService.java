package com.ISA.OnlyBunsBackend.security.auth;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptService {

    private final int MAX_ATTEMPTS = 5;
    private final long TIME_WINDOW_MILLIS = 60_000; // 1 minut

    private Map<String, Attempt> attempts;

    @PostConstruct
    public void init() {
        attempts = new ConcurrentHashMap<>();
    }

    public boolean isBlocked(String ip) {
        Attempt attempt = attempts.get(ip);
        if (attempt == null) return false;

        long now = Instant.now().toEpochMilli();
        if (now - attempt.timestamp > TIME_WINDOW_MILLIS) {
            attempts.remove(ip);
            return false;
        }

        return attempt.count >= MAX_ATTEMPTS;
    }

    public void loginFailed(String ip) {
        long now = Instant.now().toEpochMilli();
        Attempt attempt = attempts.getOrDefault(ip, new Attempt(0, now));

        if (now - attempt.timestamp > TIME_WINDOW_MILLIS) {
            attempt = new Attempt(1, now);
        } else {
            attempt.count++;
        }

        attempts.put(ip, attempt);
    }

    public void loginSucceeded(String ip) {
        attempts.remove(ip);
    }

    private static class Attempt {
        int count;
        long timestamp;

        public Attempt(int count, long timestamp) {
            this.count = count;
            this.timestamp = timestamp;
        }
    }
}
