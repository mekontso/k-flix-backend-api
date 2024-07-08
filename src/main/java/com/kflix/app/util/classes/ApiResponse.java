package com.kflix.app.util.classes;

import com.kflix.app.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class ApiResponse {

    public static ResponseEntity<?> ok(String message, Object data) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto(true, HttpStatus.OK.value(),message, data,null));
    }

    public static ResponseEntity<?> created(String message, Object data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto(true, HttpStatus.CREATED.value(),message, data,null));
    }

    public static ResponseEntity<?> badRequest(String message, Object errors){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto(false, HttpStatus.BAD_REQUEST.value(),message, null,errors));
    }

    public static ResponseEntity<?> unauthorized(String message) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDto(false, HttpStatus.UNAUTHORIZED.value(),message,null,null));
    }

    public static ResponseEntity<?> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDto(false, HttpStatus.NOT_FOUND.value(),message,null,null));
    }

    public static ResponseEntity<?> internalServerError(String message, Object errors){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto(false, HttpStatus.INTERNAL_SERVER_ERROR.value(),message,null,errors));
    }
}
