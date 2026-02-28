package com.alura.literAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long gutenbergId;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String idioma; // solo el primer idioma

    private Integer numeroDescargas;

    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro() {}

    public Libro(Long gutenbergId, String titulo, String idioma, Integer numeroDescargas, Autor autor) {
        this.gutenbergId = gutenbergId;
        this.titulo = titulo;
        this.idioma = idioma;
        this.numeroDescargas = numeroDescargas;
        this.autor = autor;
    }

    public Long getId() { return id; }
    public Long getGutenbergId() { return gutenbergId; }
    public String getTitulo() { return titulo; }
    public String getIdioma() { return idioma; }
    public Integer getNumeroDescargas() { return numeroDescargas; }
    public Autor getAutor() { return autor; }

    @Override
    public String toString() {
        return """
               ----------------------------
               Título: %s
               Autor: %s
               Idioma: %s
               Descargas: %d
               Gutenberg ID: %d
               """.formatted(titulo, autor.getNombre(), idioma, numeroDescargas, gutenbergId);
    }
}