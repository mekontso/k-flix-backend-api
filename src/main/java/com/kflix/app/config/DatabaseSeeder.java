package com.kflix.app.config;

import com.github.f4b6a3.ksuid.KsuidCreator;
import com.kflix.app.entity.Content;
import com.kflix.app.entity.ContentType;
import com.kflix.app.entity.Genre;
import com.kflix.app.exception.APIGlobalException;
import com.kflix.app.repository.ContentRepository;
import com.kflix.app.repository.ContentTypeRepository;
import com.kflix.app.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private final GenreRepository genreRepository;
    private final ContentTypeRepository contentTypeRepository;
    private final ContentRepository contentRepository;

    private String sampleFile() throws IOException {
        String fileName = "5 - The Anatomy of a Flutter App.mp4";
        String path = "/home/neoworlder/k-flix/";
        Path uploadPath = Paths.get(System.getProperty("user.home")+ new AppProperties().getContentUploadPath());
        String fileCode = String.valueOf(KsuidCreator.getKsuid());
        String fileExtension = getFileExtension(fileName);
        String newFileName = fileCode + fileExtension;

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        InputStream in = new FileInputStream(new File(path + fileName));
        Path filePath = uploadPath.resolve(newFileName);
        Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
        return newFileName;
    }

    @Override
    public void run(String... args) throws Exception {
        seedGenres();
        seedContentTypes();
        seedContents();
    }

    private void seedGenres() {
        if (genreRepository.count() == 0) {
            List<Genre> genres = List.of(
                    Genre.builder().name("Action").build(),
                    Genre.builder().name("Drama").build(),
                    Genre.builder().name("Comedy").build(),
                    Genre.builder().name("Horror").build(),
                    Genre.builder().name("Sci-Fi").build()
            );
            genreRepository.saveAll(genres);
        }
    }

    private void seedContentTypes() {
        if (contentTypeRepository.count() == 0) {
            List<ContentType> contentTypes = List.of(
                    ContentType.builder().name("Movie").build(),
                    ContentType.builder().name("TV Show").build(),
                    ContentType.builder().name("Documentary").build(),
                    ContentType.builder().name("Short Film").build(),
                    ContentType.builder().name("Music Video").build()
            );
            contentTypeRepository.saveAll(contentTypes);
        }
    }

    private void seedContents() throws IOException {
        if (contentRepository.count() == 0) {
            List<Content> contents = List.of(
                    new Content("The Great Adventure", "An epic adventure movie that spans across different worlds.", LocalDate.of(2023, 11, 10), 120, "adventure-thumbnail.jpg", sampleFile(), 2048000000L, contentTypeRepository.findContentTypeByName("Movie").get(), genreRepository.findGenreByName("Action").get()),
                    new Content("Laugh Out Loud", "A hilarious comedy that will keep you laughing from start to finish.", LocalDate.of(2022, 5, 15), 90, "laugh_out_loud-thumbnail.jpg", sampleFile(), 1024000000L, contentTypeRepository.findContentTypeByName("Movie").get(), genreRepository.findGenreByName("Comedy").get()),
                    new Content("Dark Shadows", "A horror movie that will send chills down your spine.", LocalDate.of(2021, 10, 31), 110, "dark_shadows-thumbnail.jpg", sampleFile(), 1536000000L, contentTypeRepository.findContentTypeByName("Movie").get(), genreRepository.findGenreByName("Horror").get()),
                    new Content("Science Wonders", "A fascinating documentary exploring the wonders of science.", LocalDate.of(2023, 4, 22), 60, "science_wonders-thumbnail.jpg", sampleFile(), 512000000L, contentTypeRepository.findContentTypeByName("Documentary").get(), genreRepository.findGenreByName("Sci-Fi").get()),
                    new Content("Heartfelt Drama", "A drama series that touches on the emotional journey of its characters.", LocalDate.of(2022, 9, 10), 45, "heartfelt_drama-thumbnail.jpg", sampleFile(), 768000000L, contentTypeRepository.findContentTypeByName("TV Show").get(), genreRepository.findGenreByName("Drama").get())
            );
            contentRepository.saveAll(contents);
        }
    }

    private  String getFileExtension(String fileName) {
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
        } else {
            throw new APIGlobalException(HttpStatus.MULTI_STATUS, "Please select a file with an extension.", null);
        }
        return fileExtension;
    }
}
