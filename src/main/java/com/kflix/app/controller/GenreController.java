package com.kflix.app.controller;

import com.kflix.app.dto.GenreDto;
import com.kflix.app.dto.GenreDtoCreate;
import com.kflix.app.service.GenreService;
import com.kflix.app.util.AppConstants;
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable Long id) {
        return genreService.deleteGenre(id);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getGenre(@PathVariable Long id) {
        return genreService.getGenre(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(@RequestParam(name = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNumber,
                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return genreService.getAllGenre(pageNumber, pageSize, sortBy, sortDir);
    }
}
