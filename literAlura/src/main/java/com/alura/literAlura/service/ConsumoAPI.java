package com.alura.literAlura.service;

import java.net.URI;
import java.net.http.*;
import java.time.Duration;

public class ConsumoAPI {

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public String obtenerDatos(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(15))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return response.body();
            }

            throw new RuntimeException("Error HTTP " + response.statusCode() + " Body: " + response.body());

        } catch (Exception e) {
            throw new RuntimeException("Error al consumir la API: " + e.getMessage(), e);
        }
    }
}