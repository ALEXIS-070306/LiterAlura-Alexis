package com.alura.literAlura.repository;

import com.alura.literAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombreIgnoreCase(String nombre);

    List<Autor> findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(Integer y1, Integer y2);

    List<Autor> findByAnioNacimientoLessThanEqualAndAnioFallecimientoIsNull(Integer y);
}