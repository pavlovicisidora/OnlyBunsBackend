package com.ISA.OnlyBunsBackend.loadbalancer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@RestController
public class LoadBalancerController {
    private final LoadBalancer loadBalancer;
    private final RestTemplate restTemplate;

    @Value("${server.port}")
    private int currentInstancePort;

    public LoadBalancerController(LoadBalancer loadBalancer, RestTemplate restTemplate) {
        this.loadBalancer = loadBalancer;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/hello-from-backend")
    public String helloFromBackend() {
        return "Hello from backend service running on port: " + currentInstancePort;
    }

    @GetMapping("/call-backend-manual-lb")
    public String callSelfViaLoadBalancer() {
        int maxAttempts = 5;
        long initialDelayMillis = 1000;
        double multiplier = 2.0;

        String targetUrl = "";

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                targetUrl = loadBalancer.getNextServiceUrl() + "/hello-from-backend";
                System.out.println("--- Pokusaj " + attempt + ": Iniciram poziv na '" + targetUrl + "' s porta: " + currentInstancePort + " ---");

                String response = restTemplate.getForObject(targetUrl, String.class);
                System.out.println("--- Pokusaj " + attempt + ": Primljen odgovor: " + response + " ---");
                return "Manual Load Balancer je pozvao: " + response;
            } catch (ResourceAccessException e) {
                System.err.println("--- Pokusaj " + attempt + ": Nije moguće pristupiti servisu na URL-u: " + targetUrl + ". Poruka: " + e.getMessage() + " ---");

                if (attempt < maxAttempts) {
                    long delay = (long) (initialDelayMillis * Math.pow(multiplier, attempt - 1));
                    System.out.println("--- Cekam " + delay + " ms pre sledeceg pokusaja... ---");
                    try {
                        TimeUnit.MILLISECONDS.sleep(delay);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        return "Load Balancer prekinut tokom cekanja.";
                    }
                }
            } catch (Exception e) {
                System.err.println("--- Pokusaj " + attempt + ": Doslo je do neocekivane greske: " + e.getMessage() + " ---");
                if (attempt < maxAttempts) {
                    long delay = (long) (initialDelayMillis * Math.pow(multiplier, attempt - 1));
                    System.out.println("--- Cekam " + delay + " ms pre sledeceg pokusaja... ---");
                    try {
                        TimeUnit.MILLISECONDS.sleep(delay);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        return "Load Balancer prekinut tokom cekanja.";
                    }
                }
            }
        }
        return "Nakon " + maxAttempts + " pokusaja, nije moguce dobiti odgovor od backend servisa.";
    }
}
