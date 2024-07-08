package com.kflix.app.controller;

import com.kflix.app.dto.GenreDto;
import com.kflix.app.dto.GenreDtoCreate;
import com.kflix.app.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/genre")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @PostMapping("/create")
    public ResponseEntity<?> createGenre(@Valid @RequestBody GenreDtoCreate genreDtoCreate) {
        return genreService.createGenre(genreDtoCreate);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGenre(@PathVariable Long id, @Valid @RequestBody GenreDto genreDto) {
        return genreService.updateGenre(id, genreDto);
    }
}
