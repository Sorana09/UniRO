package com.example.personalizedLearningPlatform;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class PersonalizedLearningPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalizedLearningPlatformApplication.class, args);
    }


}
