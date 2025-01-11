package com.example.personalizedLearningPlatform.exception;

import com.example.personalizedLearningPlatform.exception.common.EntityServiceException;
import com.example.personalizedLearningPlatform.service.UserService;
import org.springframework.http.HttpStatus;

public class WrongPasswordException extends EntityServiceException {
    public WrongPasswordException() {
        super(HttpStatus.BAD_REQUEST, "Wrong password");
    }
}
