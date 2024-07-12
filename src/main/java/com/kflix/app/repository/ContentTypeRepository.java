package com.kflix.app.repository;

import com.kflix.app.entity.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentTypeRepository extends JpaRepository<ContentType, Long> {
}
