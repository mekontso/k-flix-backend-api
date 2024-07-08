package com.kflix.app.service.impl;

import com.kflix.app.dto.GenreDto;
import com.kflix.app.dto.GenreDtoCreate;
import com.kflix.app.entity.Genre;
import com.kflix.app.repository.GenreRepository;
import com.kflix.app.service.GenreService;
import com.kflix.app.util.classes.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(GenreServiceImpl.class);

    @Override
    public ResponseEntity<?> createGenre(GenreDtoCreate genreDtoCreate) {
        try {
            var genre = new Genre();

            if (genreRepository.existsByName(genreDtoCreate.getName())) {
                return ApiResponse.badRequest("Genre already exist with name " + genreDtoCreate.getName(), null);
            }
            genre = modelMapper.map(genreDtoCreate, Genre.class);
            genreRepository.save(genre);
            return ApiResponse.created("Genre created successfully", modelMapper.map(genre, GenreDto.class));
        } catch (Exception e) {
            logger.error(e.toString());
            return ApiResponse.internalServerError("Error creating genre", null);
        }
    }

    @Override
    public ResponseEntity<?> updateGenre(Long id, GenreDto genreDto) {
        try {
            var genre = genreRepository.findById(id).orElse(null);

            if (genre == null) {
                return ApiResponse.notFound("Genre with id " + id + " not found");
            }
            if (genreRepository.existsByName(genreDto.getName())) {
                return ApiResponse.badRequest("Genre already exist with name " + genreDto.getName(), null);
            }
            genre.setName(genreDto.getName());
            genreRepository.save(genre);
            return ApiResponse.ok("Genre updated successfully", modelMapper.map(genre, GenreDto.class));
        } catch (Exception e) {
            logger.error(e.toString());
            return ApiResponse.internalServerError("Error updating genre", null);
        }
    }

    @Override
    public ResponseEntity<?> deleteGenre(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> getGenre(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllGenre(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        return null;
    }
}
