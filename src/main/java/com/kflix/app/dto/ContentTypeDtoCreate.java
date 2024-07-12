package com.kflix.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ContentTypeDtoCreate {
    private Long id;
    @NotBlank(message = "Content type name is required")
    private String name;
}
