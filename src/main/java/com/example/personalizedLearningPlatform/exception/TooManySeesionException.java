package com.example.personalizedLearningPlatform.exception;

import com.example.personalizedLearningPlatform.exception.common.SessionException;
import org.springframework.http.HttpStatus;

public class TooManySeesionException extends SessionException {
    public TooManySeesionException() {
        super(HttpStatus.TOO_MANY_REQUESTS, "User have too many sessions");
    }
}
