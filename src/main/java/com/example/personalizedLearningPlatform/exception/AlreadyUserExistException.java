package com.example.personalizedLearningPlatform.exception;

import com.example.personalizedLearningPlatform.exception.common.EntityException;
import org.springframework.http.HttpStatus;

public class AlreadyUserExistException extends EntityException {

        public AlreadyUserExistException() {
            super(HttpStatus.NOT_FOUND, "Entity already exists");
        }
}
