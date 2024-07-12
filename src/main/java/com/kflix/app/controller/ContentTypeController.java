package com.kflix.app.controller;

import com.kflix.app.dto.ContentTypeDto;
import com.kflix.app.dto.ContentTypeDtoCreate;
import com.kflix.app.service.ContentTypeService;
import com.kflix.app.util.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/content-type")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ContentTypeController {

    private final ContentTypeService contentTypeService;

    @PostMapping("/create")
    public ResponseEntity<?> createContentType(@Valid @RequestBody ContentTypeDtoCreate contentTypeDtoCreate) {
        return contentTypeService.createContentType(contentTypeDtoCreate);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateContentType(@PathVariable Long id, @Valid @RequestBody ContentTypeDto contentTypeDto) {
        return contentTypeService.updateContentType(id, contentTypeDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteContentType(@PathVariable Long id) {
        return contentTypeService.deleteContentType(id);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getContentType(@PathVariable Long id) {
        return contentTypeService.getContentType(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllContentTypes(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return contentTypeService.getAllContentType(pageNumber, pageSize, sortBy, sortDir);
    }
}
