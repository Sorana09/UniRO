package com.example.personalizedLearningPlatform.exception.common;

import org.springframework.http.HttpStatus;

public class EntityException extends EntityServiceException {

    public EntityException(HttpStatus status, String message) {
        super(status, message);
    }
}
