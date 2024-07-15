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
import com.kflix.app.util.classes.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            // save to local storage
            String filename = LocalFileManager.saveFile(
                    contentCreateDto.getFile().getOriginalFilename(),
                    appProperties.getContentUploadPath(),
                    contentCreateDto.getFile());

            Optional<ContentType> contentTypeOptional = contentTypeRepository.findContentTypeByName(contentCreateDto.getContentTypeName());

            if (contentTypeOptional.isEmpty()) {
                return ApiResponse.notFound("Content type with name " + contentCreateDto.getContentTypeName() + " not found");
            }

            Optional<Genre> genreOptional = genreRepository.findGenreByName(contentCreateDto.getGenreName());

            if (genreOptional.isEmpty()){
                return ApiResponse.notFound("Genre with name " + contentCreateDto.getContentTypeName() + " not found");
            }

            Content content = modelMapper.map(contentCreateDto, Content.class);

            content.setContentType(contentTypeOptional.get());
            content.setGenre(genreOptional.get());
            content.setFilename(filename);
            content.setSize(contentCreateDto.getFile().getSize());
            contentRepository.save(content);

            return ApiResponse.ok("Success creating content", modelMapper.map(content, ContentDto.class));
        } catch (RuntimeException re) {
            logger.error("Unexpected error while creating content with title {}: {}", contentCreateDto.getTitle(), re.getMessage(), re);
            return ApiResponse.internalServerError("Unexpected error while creating content", re.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteContent(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> getContent(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllContent(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        return null;
    }
}
