package com.kflix.app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GenreDtoCreate{
    Long id;
    @NotNull(message = "Genre name is required") String name;
}
