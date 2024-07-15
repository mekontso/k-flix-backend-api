package com.kflix.app.controller;

import com.kflix.app.dto.ContentCreateDto;
import com.kflix.app.service.ContentService;
import com.kflix.app.util.classes.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/content")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;

    @PostMapping("/create")
    public ResponseEntity<?> createContent(@Valid ContentCreateDto contentCreateDto, BindingResult bindingResult) {
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
}
