package com.ISA.OnlyBunsBackend.loadbalancer;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LoadBalancer {
    private final List<String> serviceUrls;
    private final AtomicInteger counter = new AtomicInteger(0);

    public LoadBalancer(List<String> serviceUrls) {
        this.serviceUrls = serviceUrls;
    }

    public String getNextServiceUrl() {
        if (serviceUrls.isEmpty()) {
            throw new IllegalStateException("No backend service URLs available for Round Robin.");
        }

        int index = counter.getAndIncrement() % serviceUrls.size();

        if (counter.get() >= Integer.MAX_VALUE - 100) {
            counter.set(0);
        }

        String selectedUrl = serviceUrls.get(index);
        System.out.println("Selected URL for next request (Round Robin): " + selectedUrl);
        return selectedUrl;
    }
}
