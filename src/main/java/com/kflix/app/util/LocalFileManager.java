package com.kflix.app.util;


import com.github.f4b6a3.ksuid.KsuidCreator;
import com.kflix.app.exception.APIGlobalException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class LocalFileManager {
    public static String saveFile(String fileName,String path, MultipartFile file) {
        Path uploadPath = Paths.get(System.getProperty("user.home") + path);
        String fileCode = String.valueOf(KsuidCreator.getKsuid());
        String fileExtension = getFileExtension(fileName);
        String newFileName = fileCode + fileExtension;

        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (Exception e) {
                throw new APIGlobalException(HttpStatus.MULTI_STATUS, "Could not create the directory where the uploaded files will be stored.", null);
            }
        }

        try(InputStream in = file.getInputStream()) {
            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new APIGlobalException(HttpStatus.MULTI_STATUS, "Could not save the file.", null);
        }
        return newFileName;
    }

    public static Resource loadFileAsResource(String fileName, String path) {
        try {
            Path filePath = Paths.get(System.getProperty("user.home") + path).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new APIGlobalException(HttpStatus.NOT_FOUND, "File not found: " + fileName, null);
            }
        } catch (MalformedURLException ex) {
            throw new APIGlobalException(HttpStatus.NOT_FOUND, "File not found: " + fileName, null);
        }
    }

    private static String getFileExtension(String fileName) {
        String fileExtension = null;

        if (fileName == null || fileName.isEmpty()) {
            throw new APIGlobalException(HttpStatus.MULTI_STATUS, "Please select a file to upload.", null);
        }

        // Find the last index of the dot character, which separates the base filename from the extension
        int dotIndex = fileName.lastIndexOf('.');

        // Check if the dot is in the filename and is not the first character
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            // Return the substring from the dot to the end of the string
            fileExtension = fileName.substring(dotIndex);
        }else {
            throw new APIGlobalException(HttpStatus.MULTI_STATUS, "Please select a file with an extension.", null);
        }
        return fileExtension;
    }
}
