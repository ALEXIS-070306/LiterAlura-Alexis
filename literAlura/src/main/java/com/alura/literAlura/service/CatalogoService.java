package com.alura.literAlura.service;

import com.alura.literAlura.dto.*;
import com.alura.literAlura.model.*;
import com.alura.literAlura.repository.AutorRepository;
import com.alura.literAlura.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CatalogoService {

    private static final String URL_BASE = "https://gutendex.com/books/?search=";

    private final LibroRepository libroRepo;
    private final AutorRepository autorRepo;

    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();

    public CatalogoService(LibroRepository libroRepo, AutorRepository autorRepo) {
        this.libroRepo = libroRepo;
        this.autorRepo = autorRepo;
    }

    @Transactional
    public Libro buscarYGuardarPrimerLibroPorTitulo(String titulo) {
        String encoded = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
        String json = consumoAPI.obtenerDatos(URL_BASE + encoded);

        DatosRespuesta respuesta = conversor.obtenerDatos(json, DatosRespuesta.class);
        if (respuesta.results() == null || respuesta.results().isEmpty()) return null;

        DatosLibro dtoLibro = respuesta.results().get(0);

        if (libroRepo.existsByGutenbergId(dtoLibro.id())) {
            return libroRepo.findByGutenbergId(dtoLibro.id()).orElse(null);
        }

        String idioma = (dtoLibro.idiomas() == null || dtoLibro.idiomas().isEmpty())
                ? "unknown"
                : dtoLibro.idiomas().get(0);

        DatosAutor dtoAutor = (dtoLibro.autores() == null || dtoLibro.autores().isEmpty())
                ? null
                : dtoLibro.autores().get(0);

        Autor autor = obtenerOCrearAutor(dtoAutor);

        Libro libro = new Libro(dtoLibro.id(), dtoLibro.titulo(), idioma, dtoLibro.numeroDescargas(), autor);
        return libroRepo.save(libro);
    }

    private Autor obtenerOCrearAutor(DatosAutor dtoAutor) {
        String nombre = "Desconocido";
        Integer nac = null;
        Integer def = null;

        if (dtoAutor != null && dtoAutor.nombre() != null && !dtoAutor.nombre().isBlank()) {
            nombre = dtoAutor.nombre();
            nac = dtoAutor.anioNacimiento();
            def = dtoAutor.anioFallecimiento();
        }

        String finalNombre = nombre;
        Integer finalNac = nac;
        Integer finalDef = def;

        return autorRepo.findByNombreIgnoreCase(finalNombre)
                .orElseGet(() -> autorRepo.save(new Autor(finalNombre, finalNac, finalDef)));
    }

    public List<Libro> listarLibros() {
        return libroRepo.findAll();
    }

    public List<Libro> listarLibrosPorIdioma(String idioma) {
        return libroRepo.findByIdiomaIgnoreCase(idioma);
    }

    public List<Autor> listarAutores() {
        List<Autor> autores = autorRepo.findAll();
        autores.sort(Comparator.comparing(a -> a.getNombre().toLowerCase()));
        return autores;
    }

    public List<Autor> autoresVivosEn(int anio) {
        List<Autor> a1 = autorRepo.findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(anio, anio);
        List<Autor> a2 = autorRepo.findByAnioNacimientoLessThanEqualAndAnioFallecimientoIsNull(anio);

        Map<String, Autor> unicos = new LinkedHashMap<>();
        for (Autor a : a1) unicos.put(a.getNombre().toLowerCase(), a);
        for (Autor a : a2) unicos.put(a.getNombre().toLowerCase(), a);

        return new ArrayList<>(unicos.values());
    }

    public long contarLibrosPorIdioma(String idioma) {
        return libroRepo.countByIdiomaIgnoreCase(idioma);
    }

    public Set<String> idiomasDisponibles() {
        return libroRepo.findAll().stream()
                .map(l -> l.getIdioma().toLowerCase())
                .collect(Collectors.toSet());
    }
}