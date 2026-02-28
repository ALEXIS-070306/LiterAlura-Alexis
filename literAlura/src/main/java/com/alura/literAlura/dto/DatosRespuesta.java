package com.alura.literAlura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosRespuesta(
        Integer count,
        String next,
        String previous,
        List<DatosLibro> results
) {}