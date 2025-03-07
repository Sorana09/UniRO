package com.example.personalizedLearningPlatform.aspect;

import com.example.personalizedLearningPlatform.dto.UserDto;
import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import com.example.personalizedLearningPlatform.entity.UserEntity;
import com.example.personalizedLearningPlatform.metrics.ViewUserMetric;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@AllArgsConstructor
@Component
public class UserAspect {

    private final ViewUserMetric viewUserMetric;

    @AfterReturning(
            value = "execution(* com.example.personalizedLearningPlatform.controller.UserController.signup(..))",
            returning = "responseEntity"
    )
    public void afterReturningSignup(ResponseEntity<UserDto> responseEntity){
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            UserDto user = responseEntity.getBody();

            if(user != null) {
                Integer userId = user.getId();
                viewUserMetric.registerViewForUser(userId);
            }
        }

    }
}
