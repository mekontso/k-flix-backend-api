package com.kflix.app.config;

import org.springframework.beans.factory.annotation.Value;


public class AppProperties {
    @Value("${app.content.upload.path}")
    public static String contentUploadPath;

    @Value("${app.content.thumbnail.upload.path}")
    public static String contentThumbnailUploadPath;
}
