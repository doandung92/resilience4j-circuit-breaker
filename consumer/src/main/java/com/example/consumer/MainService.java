package com.example.consumer;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MainService {
    private final RestTemplate restTemplate;

    public String normal(){
        return "OK";
    }

    @CircuitBreaker(name = "backendA", fallbackMethod = "fallback")
    public String slow(){
        return restTemplate.getForObject("http://localhost:9000/slow", String.class);
    }

    @CircuitBreaker(name = "backendA", fallbackMethod = "fallback")
    public String fail(){
        return restTemplate.getForObject("http://localhost:9000/fail", String.class);
    }

    public String fallback(Exception e){
        return "Provider Service is down";
    }
}
