package com.kflix.app.dto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class ContentCreateDto {
    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Title should not exceed 100 characters")
    private String title;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 1000, message = "Description should not exceed 1000 characters")
    private String description;

    @NotNull(message = "Release date is mandatory")
    @PastOrPresent(message = "Release date cannot be in the future")
    private LocalDate releaseDate;

    @NotNull(message = "Duration is mandatory")
    @Positive(message = "Duration must be a positive number")
    private Integer duration;

    private String thumbnail;

    private Long size;

    private MultipartFile file;

    @NotBlank(message = "Content type is mandatory")
    private String contentTypeName;

    @NotBlank(message = "Genre name is mandatory")
    private String genreName;
}
