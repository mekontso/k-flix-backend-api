package com.kflix.app.repository;

import com.kflix.app.entity.ContentType;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentTypeRepository extends JpaRepository<ContentType, Long> {
    boolean existsByName(@NotBlank(message = "Content type name is required") String name);
    Optional<ContentType> findContentTypeByName(String name);
}
