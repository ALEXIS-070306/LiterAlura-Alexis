package com.alura.literAlura.principal;

import com.alura.literAlura.model.Autor;
import com.alura.literAlura.model.Libro;
import com.alura.literAlura.service.CatalogoService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    private final CatalogoService catalogo;
    private final Scanner scanner = new Scanner(System.in);

    public Principal(CatalogoService catalogo) {
        this.catalogo = catalogo;
    }

    public void mostrarMenu() {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\nElija la opción a través de su número:");
            System.out.println("1- buscar libro por titulo");
            System.out.println("2- listar libros registrados");
            System.out.println("3- listar autores registrados");
            System.out.println("4- listar autores vivos en un determinado ano");
            System.out.println("5- listar libros por idioma");
            System.out.println("6- exhibir cantidad de libros en un determinado idioma");
            System.out.println("0- salir");
            System.out.print("> ");

            opcion = leerEnteroSeguro();

            switch (opcion) {
                case 1 -> buscarLibro();
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> autoresVivos();
                case 5 -> librosPorIdioma();
                case 6 -> estadisticaPorIdioma();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void buscarLibro() {
        System.out.print("Ingrese el título del libro: ");
        String titulo = scanner.nextLine().trim();
        titulo = titulo.replaceAll("\\s+", " ");

        if (titulo.isBlank()) {
            System.out.println("El título no puede estar vacío.");
            return;
        }

        Libro libro = catalogo.buscarYGuardarPrimerLibroPorTitulo(titulo);

        if (libro == null) {
            System.out.println("No se encontró ningún libro con: " + titulo);
            return;
        }

        System.out.println("\nLibro registrado/encontrado:");
        System.out.println(libro);
    }

    private void listarLibros() {
        List<Libro> libros = catalogo.listarLibros();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados todavía.");
            return;
        }
        System.out.println("\nLibros registrados:");
        libros.forEach(System.out::println);
    }

    private void listarAutores() {
        List<Autor> autores = catalogo.listarAutores();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados todavía.");
            return;
        }
        System.out.println("\nAutores registrados:");
        autores.forEach(a -> System.out.println("- " + a));
    }

    private void autoresVivos() {
        System.out.print("Ingrese el año: ");
        int anio = leerAnioSeguro();

        List<Autor> autores = catalogo.autoresVivosEn(anio);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año: " + anio);
            return;
        }

        System.out.println("\nAutores vivos en " + anio + ":");
        autores.forEach(a -> System.out.println("- " + a));
    }

    private void librosPorIdioma() {
        var idiomas = catalogo.idiomasDisponibles();
        if (!idiomas.isEmpty()) {
            System.out.println("Idiomas disponibles en la base: " + idiomas);
        }
        System.out.print("Ingrese el idioma (ej. es, en, fr): ");
        String idioma = scanner.nextLine().trim().toLowerCase();

        if (idioma.isBlank()) {
            System.out.println("El idioma no puede estar vacío.");
            return;
        }

        List<Libro> libros = catalogo.listarLibrosPorIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No hay libros en el idioma: " + idioma);
            return;
        }

        System.out.println("\nLibros en idioma '" + idioma + "':");
        libros.forEach(System.out::println);
    }

    private void estadisticaPorIdioma() {
        System.out.println("Elija el idioma:");
        System.out.println("1- en");
        System.out.println("2- es");
        System.out.print("> ");

        int op = leerEnteroSeguro();
        String idioma = (op == 1) ? "en" : (op == 2) ? "es" : null;

        if (idioma == null) {
            System.out.println("Opción inválida.");
            return;
        }

        long total = catalogo.contarLibrosPorIdioma(idioma);
        System.out.println("Cantidad de libros en '" + idioma + "': " + total);
    }

    private int leerEnteroSeguro() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Escriba un número: ");
            }
        }
    }

    private int leerAnioSeguro() {
        while (true) {
            int anio = leerEnteroSeguro();
            if (anio < -5000 || anio > 3000) {
                System.out.print("Año fuera de rango. Intente nuevamente: ");
                continue;
            }
            return anio;
        }
    }
}