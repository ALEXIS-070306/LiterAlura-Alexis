package com.alura.literAlura.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos {

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return mapper.readValue(json, clase);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo convertir JSON: " + e.getMessage(), e);
        }
    }
}