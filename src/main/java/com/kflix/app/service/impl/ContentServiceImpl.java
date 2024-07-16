package com.kflix.app.service.impl;

import com.kflix.app.config.AppProperties;
import com.kflix.app.dto.ContentCreateDto;
import com.kflix.app.dto.ContentDto;
import com.kflix.app.entity.Content;
import com.kflix.app.entity.ContentType;
import com.kflix.app.entity.Genre;
import com.kflix.app.repository.ContentRepository;
import com.kflix.app.repository.ContentTypeRepository;
import com.kflix.app.repository.GenreRepository;
import com.kflix.app.service.ContentService;
import com.kflix.app.util.LocalFileManager;
import com.kflix.app.util.Util;
import com.kflix.app.util.classes.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kflix.app.util.AppConstants.DEFAULT_CHUNK_SIZE;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {
    private final AppProperties appProperties;
    private final Logger logger = LoggerFactory.getLogger(ContentServiceImpl.class);
    private final ContentRepository contentRepository;
    private final ContentTypeRepository contentTypeRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> createContent(ContentCreateDto contentCreateDto) {
        try {
            // Save to local storage
            String filename = LocalFileManager.saveFile(
                    contentCreateDto.getFile().getOriginalFilename(),
                    appProperties.getContentUploadPath(),
                    contentCreateDto.getFile());

            Optional<ContentType> contentTypeOptional = contentTypeRepository.findContentTypeByName(contentCreateDto.getContentTypeName());
            Optional<Genre> genreOptional = genreRepository.findGenreByName(contentCreateDto.getGenreName());

            if (contentTypeOptional.isEmpty()) {
                return ApiResponse.notFound("Content type with name " + contentCreateDto.getContentTypeName() + " not found");
            }

            if (genreOptional.isEmpty()) {
                return ApiResponse.notFound("Genre with name " + contentCreateDto.getGenreName() + " not found");
            }

            Content content = modelMapper.map(contentCreateDto, Content.class);
            content.setContentType(contentTypeOptional.get());
            content.setGenre(genreOptional.get());
            content.setFilename(filename);
            content.setSize(contentCreateDto.getFile().getSize());
            contentRepository.save(content);

            return ApiResponse.ok("Success creating content", modelMapper.map(content, ContentDto.class));
        } catch (DataAccessException dae) {
            logger.error("Database access error while creating content with title {}: {}", contentCreateDto.getTitle(), dae.getMessage(), dae);
            return ApiResponse.internalServerError("Database error while creating content", null);
        } catch (RuntimeException re) {
            logger.error("Unexpected error while creating content with title {}: {}", contentCreateDto.getTitle(), re.getMessage(), re);
            return ApiResponse.internalServerError("Unexpected error while creating content", re.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteContent(Long id) {
        try {
            if (!contentRepository.existsById(id)) {
                return ApiResponse.notFound("Content with id " + id + " not found");
            }
            contentRepository.deleteById(id);
            return ApiResponse.ok("Content deleted successfully", null);
        } catch (DataAccessException dae) {
            logger.error("Database access error while deleting content with id {}: {}", id, dae.getMessage(), dae);
            return ApiResponse.internalServerError("Database error while deleting content", null);
        } catch (RuntimeException re) {
            logger.error("Unexpected error while deleting content with id {}: {}", id, re.getMessage(), re);
            return ApiResponse.internalServerError("Unexpected error while deleting content", null);
        }
    }

    @Override
    public ResponseEntity<?> getContent(Long id) {
        try {
            Optional<Content> contentOptional = contentRepository.findById(id);
            if (contentOptional.isEmpty()) {
                return ApiResponse.notFound("Content with id " + id + " not found");
            }
            return ApiResponse.ok("Content retrieved successfully", modelMapper.map(contentOptional.get(), ContentDto.class));
        } catch (DataAccessException dae) {
            logger.error("Database access error while retrieving content with id {}: {}", id, dae.getMessage(), dae);
            return ApiResponse.internalServerError("Database error while retrieving content", null);
        } catch (RuntimeException re) {
            logger.error("Unexpected error while retrieving content with id {}: {}", id, re.getMessage(), re);
            return ApiResponse.internalServerError("Unexpected error while retrieving content", null);
        }
    }

    @Override
    public ResponseEntity<?> getAllContent(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        try {
            Sort.Direction direction = Sort.Direction.fromString(sortDirection);
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortBy));
            Page<Content> queryResponse = contentRepository.findAll(pageable);

            var contentDtoList = queryResponse.getContent()
                    .stream()
                    .map(content -> modelMapper.map(content, ContentDto.class))
                    .collect(Collectors.toList());

            return ApiResponse.ok("Success getting content list.", Util.pageData(contentDtoList, queryResponse));
        } catch (IllegalArgumentException e) {
            logger.error("Invalid sort direction: {}", sortDirection, e);
            return ApiResponse.badRequest("Invalid sort direction: " + sortDirection, null);
        } catch (DataAccessException dae) {
            logger.error("Database access error while getting content: {}", dae.getMessage(), dae);
            return ApiResponse.internalServerError("Database error while getting content", null);
        } catch (RuntimeException re) {
            logger.error("Unexpected error while getting content: {}", re.getMessage(), re);
            return ApiResponse.internalServerError("Unexpected error while getting content", null);
        }
    }

    @Override
    public ResponseEntity<?> streamContent(String filename, String rangeHeader) {
        try {
            Path filePath = Paths.get(System.getProperty("user.home") + appProperties.getContentUploadPath()).resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }


            long fileLength = resource.contentLength();
            long rangeStart = 0;
            long rangeEnd = DEFAULT_CHUNK_SIZE - 1;

            if (StringUtils.hasText(rangeHeader)) {
                // Parse the range header
                String[] ranges = rangeHeader.replace("bytes=", "").split("-");
                rangeStart = Long.parseLong(ranges[0]);
                if (ranges.length > 1) {
                    rangeEnd = Long.parseLong(ranges[1]);
                } else {
                    rangeEnd = fileLength - 1;
                }
            }

            // Ensure the ranges are within the valid range
            rangeEnd = Math.min(rangeEnd, fileLength - 1);
            if (rangeStart > rangeEnd) {
                return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
                        .header("Content-Range", "bytes */" + fileLength)
                        .build();
            }

            // Read the byte range from the file
            byte[] data;
            try (RandomAccessFile randomAccessFile = new RandomAccessFile(resource.getFile(), "r")) {
                randomAccessFile.seek(rangeStart);
                int bytesToRead = (int) (rangeEnd - rangeStart + 1);
                data = new byte[bytesToRead];
                randomAccessFile.readFully(data);
            }

            // Prepare the response with the partial content
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
            headers.add("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + fileLength);
            headers.add("Accept-Ranges", "bytes");

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .headers(headers)
                    .body(data);
        } catch (IOException e) {
            logger.error("Error streaming content: {}", filename, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
