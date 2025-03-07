package com.example.personalizedLearningPlatform.aspect;

import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.metrics.ViewUniversityMetric;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class UniversityAspect {

    private final ViewUniversityMetric viewUniversityMetric;

    @AfterReturning(
            value = "execution(* com.example.personalizedLearningPlatform.controller.UniversityController.getUniversityById(..))",
            returning = "responseEntity")
    public void afterReturningGetUniversityById(ResponseEntity<UniversityEntity> responseEntity){
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            UniversityEntity university = responseEntity.getBody();
            if(university != null) {
                Integer universityId = university.getId();
                viewUniversityMetric.registerViewForUniversity(universityId);
            }
        }
    }



}
