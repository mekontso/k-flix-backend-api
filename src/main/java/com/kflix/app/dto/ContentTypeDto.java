package com.kflix.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ContentTypeDto {
    private Long id;
    @NotBlank(message = "Genre name is required")
    private String name;
}
