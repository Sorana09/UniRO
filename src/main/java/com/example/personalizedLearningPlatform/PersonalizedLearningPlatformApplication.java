package com.example.personalizedLearningPlatform;

import io.micrometer.prometheusmetrics.PrometheusConfig;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PersonalizedLearningPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalizedLearningPlatformApplication.class, args);
    }


}
