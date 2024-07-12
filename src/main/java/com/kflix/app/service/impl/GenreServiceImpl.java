package com.kflix.app.service.impl;

import com.kflix.app.dto.GenreDto;
import com.kflix.app.dto.GenreDtoCreate;
import com.kflix.app.entity.Genre;
import com.kflix.app.repository.GenreRepository;
import com.kflix.app.service.GenreService;
import com.kflix.app.util.Util;
import com.kflix.app.util.classes.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private static final Logger logger = LoggerFactory.getLogger(GenreServiceImpl.class);
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

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
        try {
            if (!genreRepository.existsById(id)) {
                return ApiResponse.notFound("Genre with id " + id + " not found");
            }
            genreRepository.deleteById(id);
            return ApiResponse.ok("Genre deleted with id " + id + " successfully", null);
        } catch (Exception e) {
            logger.error(e.toString());
            return ApiResponse.internalServerError("Error deleting genre", null);
        }
    }

    @Override
    public ResponseEntity<?> getGenre(Long id) {
        try {
            var optionalGenre = genreRepository.findById(id);

            if (optionalGenre.isEmpty())
                return ApiResponse.notFound("Genre with id " + id + " not found");

            return ApiResponse.ok("Genre retrieved successfully", modelMapper.map(optionalGenre.get(), GenreDto.class));
        } catch (Exception e) {
            logger.error(e.toString());
            return ApiResponse.internalServerError("Error deleting genre", null);
        }
    }

    @Override
    public ResponseEntity<?> getAllGenre(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        try {
            Sort.Direction direction = Sort.Direction.fromString(sortDirection);
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortBy));
            Page<Genre> queryResponse = genreRepository.findAll(pageable);

            List<GenreDto> genreDtoList = queryResponse.getContent()
                    .stream()
                    .map(genre -> modelMapper.map(genre, GenreDto.class))
                    .collect(Collectors.toList());

            return ApiResponse.ok("Success getting genre list.", Util.pageData(genreDtoList, queryResponse));
        } catch (IllegalArgumentException e) {
            logger.error("Invalid sort direction: {}", sortDirection, e);
            return ApiResponse.badRequest("Invalid sort direction: " + sortDirection, null);
        } catch (Exception e) {
            logger.error("Error getting genres: {}", e.getMessage(), e);
            return ApiResponse.internalServerError("Error getting genres", null);
        }
    }
}
