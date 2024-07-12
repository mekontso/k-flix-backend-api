package com.kflix.app.service.impl;

import com.kflix.app.dto.ContentTypeDto;
import com.kflix.app.dto.ContentTypeDtoCreate;
import com.kflix.app.entity.ContentType;
import com.kflix.app.repository.ContentTypeRepository;
import com.kflix.app.service.ContentTypeService;
import com.kflix.app.util.Util;
import com.kflix.app.util.classes.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentTypeServiceImpl implements ContentTypeService {

    private static final Logger logger = LoggerFactory.getLogger(ContentTypeServiceImpl.class);
    private final ContentTypeRepository contentTypeRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> createContentType(ContentTypeDtoCreate contentTypeDtoCreate) {
        String contentTypeName = contentTypeDtoCreate.getName();
        try {
            if (contentTypeRepository.existsByName(contentTypeName)) {
                return ApiResponse.badRequest("Content type already exists with name " + contentTypeName, null);
            }
            ContentType contentType = modelMapper.map(contentTypeDtoCreate, ContentType.class);
            contentTypeRepository.save(contentType);
            ContentTypeDto contentTypeDto = modelMapper.map(contentType, ContentTypeDto.class);
            return ApiResponse.created("Content type created successfully", contentTypeDto);
        } catch (DataAccessException dae) {
            logger.error("Database access error while creating content type with name {}: {}", contentTypeName, dae.getMessage(), dae);
            return ApiResponse.internalServerError("Database error while creating content type", null);
        } catch (MappingException me) {
            logger.error("Mapping error while creating content type with name {}: {}", contentTypeName, me.getMessage(), me);
            return ApiResponse.internalServerError("Data mapping error while creating content type", null);
        } catch (RuntimeException re) {
            logger.error("Unexpected error while creating content type with name {}: {}", contentTypeName, re.getMessage(), re);
            return ApiResponse.internalServerError("Unexpected error while creating content type", null);
        }
    }

    @Override
    public ResponseEntity<?> updateContentType(Long id, ContentTypeDto contentTypeDto) {
        try {
            ContentType contentType = contentTypeRepository.findById(id).orElse(null);
            if (contentType == null) {
                return ApiResponse.notFound("Content type with id " + id + " not found");
            }
            if (contentTypeRepository.existsByName(contentTypeDto.getName())) {
                return ApiResponse.badRequest("Content type already exists with name " + contentTypeDto.getName(), null);
            }
            contentType.setName(contentTypeDto.getName());
            contentTypeRepository.save(contentType);
            return ApiResponse.ok("Content type updated successfully", modelMapper.map(contentType, ContentTypeDto.class));
        } catch (DataAccessException dae) {
            logger.error("Database access error while updating content type with id {}: {}", id, dae.getMessage(), dae);
            return ApiResponse.internalServerError("Database error while updating content type", null);
        } catch (MappingException me) {
            logger.error("Mapping error while updating content type with id {}: {}", id, me.getMessage(), me);
            return ApiResponse.internalServerError("Data mapping error while updating content type", null);
        } catch (RuntimeException re) {
            logger.error("Unexpected error while updating content type with id {}: {}", id, re.getMessage(), re);
            return ApiResponse.internalServerError("Unexpected error while updating content type", null);
        }
    }

    @Override
    public ResponseEntity<?> deleteContentType(Long id) {
        try {
            if (!contentTypeRepository.existsById(id)) {
                return ApiResponse.notFound("Content type with id " + id + " not found");
            }
            contentTypeRepository.deleteById(id);
            return ApiResponse.ok("Content type deleted with id " + id + " successfully", null);
        } catch (DataAccessException dae) {
            logger.error("Database access error while deleting content type with id {}: {}", id, dae.getMessage(), dae);
            return ApiResponse.internalServerError("Database error while deleting content type", null);
        } catch (RuntimeException re) {
            logger.error("Unexpected error while deleting content type with id {}: {}", id, re.getMessage(), re);
            return ApiResponse.internalServerError("Unexpected error while deleting content type", null);
        }
    }

    @Override
    public ResponseEntity<?> getContentType(Long id) {
        try {
            var optionalContentType = contentTypeRepository.findById(id);
            if (optionalContentType.isEmpty()) {
                return ApiResponse.notFound("Content type with id " + id + " not found");
            }
            return ApiResponse.ok("Content type retrieved successfully", modelMapper.map(optionalContentType.get(), ContentTypeDto.class));
        } catch (DataAccessException dae) {
            logger.error("Database access error while retrieving content type with id {}: {}", id, dae.getMessage(), dae);
            return ApiResponse.internalServerError("Database error while retrieving content type", null);
        } catch (RuntimeException re) {
            logger.error("Unexpected error while retrieving content type with id {}: {}", id, re.getMessage(), re);
            return ApiResponse.internalServerError("Unexpected error while retrieving content type", null);
        }
    }

    @Override
    public ResponseEntity<?> getAllContentType(int pageNumber, int pageSize, String sortBy, String sortDir) {
        try {
            Sort.Direction direction = Sort.Direction.fromString(sortDir);
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortBy));
            Page<ContentType> queryResponse = contentTypeRepository.findAll(pageable);

            List<ContentTypeDto> contentTypeDtoList = queryResponse.getContent()
                    .stream()
                    .map(contentType -> modelMapper.map(contentType, ContentTypeDto.class))
                    .collect(Collectors.toList());

            return ApiResponse.ok("Success getting content type list.", Util.pageData(contentTypeDtoList, queryResponse));
        } catch (IllegalArgumentException e) {
            logger.error("Invalid sort direction: {}", sortDir, e);
            return ApiResponse.badRequest("Invalid sort direction: " + sortDir, null);
        } catch (DataAccessException dae) {
            logger.error("Database access error while getting content types: {}", dae.getMessage(), dae);
            return ApiResponse.internalServerError("Database error while getting content types", null);
        } catch (RuntimeException re) {
            logger.error("Unexpected error while getting content types: {}", re.getMessage(), re);
            return ApiResponse.internalServerError("Unexpected error while getting content types", null);
        }
    }
}
