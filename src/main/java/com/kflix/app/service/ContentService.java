package com.kflix.app.service;

import com.kflix.app.dto.ContentCreateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ContentService {
    ResponseEntity<?> createContent(ContentCreateDto contentCreateDto);

    ResponseEntity<?> deleteContent(Long id);

    ResponseEntity<?> getContent(Long id);

    ResponseEntity<?> getAllContent(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
}
