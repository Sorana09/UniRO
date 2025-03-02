package com.example.personalizedLearningPlatform.exception.handler;

import com.example.personalizedLearningPlatform.exception.common.ErrorResponse;
import io.github.d4rckh.limiterx.spring.exception.LimiterXTooManyRequests;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(final MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(exception.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handle(final RuntimeException exception) {
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(LimiterXTooManyRequests.class)
    public ResponseEntity<ErrorResponse> handle() {
        return ResponseEntity.badRequest() // TODO: set status code 429
            .body(new ErrorResponse("stop spamming out services!"));
    }
}