package com.kflix.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class APIGlobalException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;
    private Object errors;
}
