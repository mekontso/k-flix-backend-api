package com.kflix.app.service;

import com.kflix.app.dto.GenreDtoCreate;
import org.springframework.http.ResponseEntity;

public interface GenreService {
    ResponseEntity<?> createGenre(GenreDtoCreate genreDtoCreate);
    ResponseEntity<?> updateGenre(Long id, GenreDtoCreate genreDtoCreate);
    ResponseEntity<?> deleteGenre(Long id);
    ResponseEntity<?> getGenre(Long id);
    ResponseEntity<?> getAllGenre(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
}
