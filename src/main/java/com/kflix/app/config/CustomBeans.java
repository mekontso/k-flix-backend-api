package com.kflix.app.config;


import com.kflix.app.dto.ContentDto;
import com.kflix.app.entity.Content;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CustomBeans {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // converter map Content to ContentCreateDto
        mapper.typeMap(Content.class, ContentDto.class)
                .addMappings(mapping -> {
                    mapping.map(Content::getContentType, ContentDto::setContentTypeDto);
                    mapping.map(Content::getGenre, ContentDto::setGenreDto);
                });

//        mapper
//                .addConverter(new AbstractConverter<ContentCreateDto, Content>() {
//                    @Override
//                    protected Content convert(ContentCreateDto source) {
//                        Content content = new Content();
//                        content.setTitle(source.getTitle());
//                        content.setDescription(source.getDescription());
//                        content.setReleaseDate(source.getReleaseDate());
//                        content.setDuration(source.getDuration());
//                        content.setThumbnail(source.getThumbnail());
//                        return content;
//                    }
//                });

        return mapper;
    }

}
