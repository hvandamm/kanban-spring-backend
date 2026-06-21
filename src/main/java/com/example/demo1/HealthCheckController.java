package com.example.demo1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController{

    @GetMapping("/api/ping")
    public String ping(){
        return "Spring Boot is back up and running java 21!";
    }
}