package com.example.personalizedLearningPlatform.exception.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class EntityServiceException extends RuntimeException {
    private HttpStatus status;
    private String message;
}
