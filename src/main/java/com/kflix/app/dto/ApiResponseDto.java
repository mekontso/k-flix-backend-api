package com.kflix.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDto {

    private boolean success;
    private int status;
    private String message;
    private Object data;
    private Object errors;
}
