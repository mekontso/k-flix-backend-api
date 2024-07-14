package com.kflix.app.repository;

import com.kflix.app.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {

}
