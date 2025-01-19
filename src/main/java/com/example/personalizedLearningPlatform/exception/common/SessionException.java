package com.example.personalizedLearningPlatform.exception.common;

import org.springframework.http.HttpStatus;


public class SessionException extends EntityServiceException {
    public SessionException(HttpStatus status, String message) {
        super(status, message);
    }
}
