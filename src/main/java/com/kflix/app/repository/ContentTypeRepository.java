package com.kflix.app.repository;

import com.kflix.app.entity.ContentType;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentTypeRepository extends JpaRepository<ContentType, Long> {
    boolean existsByName(@NotBlank(message = "Content type name is required") String name);
}
