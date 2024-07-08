package com.kflix.app.repository;

import com.kflix.app.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>{
    Optional<Genre> findByName(String name);
    boolean existsByName(String name);
}
