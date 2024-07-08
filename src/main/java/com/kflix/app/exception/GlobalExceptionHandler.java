package com.kflix.app.exception;


import com.kflix.app.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(APIGlobalException.class)
    public ResponseEntity<ApiResponseDto> handleAPIGlobalException(APIGlobalException exception, WebRequest request) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(false,exception.getHttpStatus().value(), exception.getMessage(), null,exception.getErrors());
        return ResponseEntity.status(exception.getHttpStatus()).body(apiResponseDto);
    }
}
