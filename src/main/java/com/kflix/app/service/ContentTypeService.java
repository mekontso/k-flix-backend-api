package com.kflix.app.service;

import com.kflix.app.dto.ContentTypeDto;
import com.kflix.app.dto.ContentTypeDtoCreate;
import org.springframework.http.ResponseEntity;

public interface ContentTypeService {
    ResponseEntity<?> createContentType(ContentTypeDtoCreate contentTypeDtoCreate);

    ResponseEntity<?> updateContentType(Long id, ContentTypeDto contentTypeDto);

    ResponseEntity<?> deleteContentType(Long id);

    ResponseEntity<?> getContentType(Long id);

    ResponseEntity<?> getAllContentType(int pageNumber, int pageSize, String sortBy, String sortDir);
}
