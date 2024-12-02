package com.example.personalizedLearningPlatform.exception;

import com.example.personalizedLearningPlatform.exception.common.EntityException;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends EntityException {

    public EntityNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Entity could not be found");
    }
}

