package com.kflix.app.service;

import com.kflix.app.dto.ContentCreateDto;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface ContentService {
    ResponseEntity<?> createContent(ContentCreateDto contentCreateDto);

    ResponseEntity<?> deleteContent(Long id);

    ResponseEntity<?> getContent(Long id);

    ResponseEntity<?> getAllContent(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

    ResponseEntity<?> streamContent(String filename, String rangeHeader);
}
