package com.kflix.app.controller;

import com.kflix.app.dto.ContentCreateDto;
import com.kflix.app.service.ContentService;
import com.kflix.app.util.AppConstants;
import com.kflix.app.util.classes.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/content")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @PostMapping("/create")
    public ResponseEntity<?> createContent(@Valid @RequestBody ContentCreateDto contentCreateDto, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
        }

        if (contentCreateDto.getFile() == null || contentCreateDto.getFile().isEmpty()) {
            errors.put("file", "File is required and must not be empty");
        }

        if (!errors.isEmpty()) {
            return ApiResponse.badRequest("Validation failed", errors);
        }

        return contentService.createContent(contentCreateDto);
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> updateContent(@PathVariable Long id, @Valid @RequestBody ContentCreateDto contentCreateDto, BindingResult bindingResult) {
//        Map<String, String> errors = new HashMap<>();
//        if (bindingResult.hasErrors()) {
//            bindingResult.getFieldErrors().forEach(error ->
//                    errors.put(error.getField(), error.getDefaultMessage()));
//        }
//
//        if (contentCreateDto.getFile() == null || contentCreateDto.getFile().isEmpty()) {
//            errors.put("file", "File is required and must not be empty");
//        }
//
//        if (!errors.isEmpty()) {
//            return ApiResponse.badRequest("Validation failed", errors);
//        }
//
//        return contentService.updateContent(id, contentCreateDto);
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteContent(@PathVariable Long id) {
        return contentService.deleteContent(id);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getContent(@PathVariable Long id) {
        return contentService.getContent(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllContents(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return contentService.getAllContent(pageNumber, pageSize, sortBy, sortDir);
    }
}
