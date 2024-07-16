package com.kflix.app.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppProperties {
    @Value("${app.content.upload.path}")
    private String contentUploadPath;

    @Value("${app.content.thumbnail.upload.path}")
    private  String contentThumbnailUploadPath;
}
