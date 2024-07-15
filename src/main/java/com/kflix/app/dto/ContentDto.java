package com.kflix.app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ContentDto {
    private String title;

    private String description;

    private LocalDate releaseDate;

    private Integer duration;

    private String thumbnail;

    private ContentTypeDto contentTypeDto;

    private GenreDto genreDto;
}
