package com.alura.literAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    private Integer anioNacimiento;
    private Integer anioFallecimiento;

    public Autor() {}

    public Autor(String nombre, Integer anioNacimiento, Integer anioFallecimiento) {
        this.nombre = nombre;
        this.anioNacimiento = anioNacimiento;
        this.anioFallecimiento = anioFallecimiento;
    }

    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getAnioNacimiento() { return anioNacimiento; }
    public void setAnioNacimiento(Integer anioNacimiento) { this.anioNacimiento = anioNacimiento; }

    public Integer getAnioFallecimiento() { return anioFallecimiento; }
    public void setAnioFallecimiento(Integer anioFallecimiento) { this.anioFallecimiento = anioFallecimiento; }

    @Override
    public String toString() {
        return nombre + " (" + anioNacimiento + " - " + anioFallecimiento + ")";
    }
}