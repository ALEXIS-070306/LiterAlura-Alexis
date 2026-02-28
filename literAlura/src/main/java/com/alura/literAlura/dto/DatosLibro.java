package com.alura.literAlura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        Long id,
        @JsonAlias("title") String titulo,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Integer numeroDescargas,
        @JsonAlias("authors") List<DatosAutor> autores
) {}
