package com.alura.literAlura.repository;

import com.alura.literAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    boolean existsByGutenbergId(Long gutenbergId);
    Optional<Libro> findByGutenbergId(Long gutenbergId);

    List<Libro> findByIdiomaIgnoreCase(String idioma);

    long countByIdiomaIgnoreCase(String idioma);
}