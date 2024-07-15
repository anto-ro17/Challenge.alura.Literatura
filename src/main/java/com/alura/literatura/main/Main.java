package com.alura.literatura.main;


import com.alura.literatura.model.*;
import com.alura.literatura.repository.LibrosRepository;
import com.alura.literatura.service.APIClient;
import com.alura.literatura.service.APIDataConverter;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final String URL_BASE = "https://gutendex.com/books";
    private APIClient consumoApi = new APIClient();
    private APIDataConverter conversor = new APIDataConverter();
    private Scanner teclado = new Scanner(System.in);
    private LibrosRepository repositorio;

    public Main(LibrosRepository repository) {
        this.repositorio = repository;
    }

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ======== Bienvenido a tu libreria  ========
                    1 - Buscar libro por titulo en la web 
                    2 - Mostrar libros registrados
                    3 - Mostrar autores registrados
                    4 - Mostrar autores vivos por año
                    5 - Mostrar libros por idioma
                    6 - Top 10 libros más descargados
                    0 - Salir

                    Elija la opción que desea utilizar
                    """;

            try {
                System.out.println(menu);
                opcion = teclado.nextInt();
                teclado.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("""
                        ---------------------------------------
                           Opción no valida, intente de nuevo
                        ---------------------------------------
                        """);
                teclado.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrarLibrosRegisrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    autoresVivosPorAno();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;
                case 6:
                    top10descargas();
                    break;
                case 0:
                    System.out.println("Gracias por utilizar nuestro servicio \n" + "Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("""
                            ---------------------------------------
                              Opción no valida, intente de nuevo
                            ---------------------------------------
                            """);
            }
        }

    }

    private DatosLibros buscarLibro() {
        System.out.println("Ingresa el nombre del libro a buscar en la Web");
        var tituloLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "/?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            System.out.println("---Libro encontrado---");
            System.out.println(libroBuscado.get());
            return libroBuscado.get();
        } else {
            System.out.println("No se encontro el libro\n");
            return null;
        }

    }

    private void buscarLibroPorTitulo() {
        Optional<DatosLibros> datosOpcional = Optional.ofNullable(buscarLibro());

        if (datosOpcional.isPresent()) {
            DatosLibros datosLibros = datosOpcional.get();
            Libro libro = new Libro(datosLibros);
            List<Autor> autores = new ArrayList<>();
            for (DatosAutor datosAutor : datosLibros.autor()) {
                Autor autor = new Autor(datosAutor);
                autor.setLibro(libro);
                autores.add(autor);
            }
            libro.setAutor(autores);
            try {
                repositorio.save(libro);
                System.out.println(libro.getTitulo() + "---Libro guaradado---");
            } catch (DataIntegrityViolationException e) {
                System.out.println("Libro no almacenado");
                System.out.println("El libro ya se encuntra almacenado en la base de datos.\n");
            }
        }
    }

    private void mostrarLibrosRegisrados() {
        List<Libro> librosRegistrados = repositorio.findAll();
        librosRegistrados.forEach(l ->
                System.out.println(
                        "Título: " + l.getTitulo() +
                                "\nAutor: " + l.getAutor().stream().map(Autor::getNombre).collect(Collectors.joining()) +
                                "\nIdioma: " + l.getIdiomas() +
                                "\nNúmero de descargas: " + l.getNumeroDeDescargas() + "\n"));
    }

    private void mostrarAutoresRegistrados() {
        List<Autor> autoresRegitrados = repositorio.mostrarAutores();
        Map<String, List<String>> autoresConLibros = autoresRegitrados.stream()
                .collect(Collectors.groupingBy(Autor::getNombre, Collectors.mapping(a -> a.getLibro().getTitulo(), Collectors.toList())));
        autoresConLibros.forEach((nombre, libros) -> {
            Autor autor = autoresRegitrados.stream()
                    .filter(a -> a.getNombre().equals(nombre))
                    .findFirst().orElse(null);
            if (autor != null) {
                System.out.println("Nombre: " + nombre);
                System.out.println("Fecha de nacimiento: " + autor.getFechaDeNacimiento());
                System.out.println("Fecha de fallecimiento: " + autor.getFechaDeFallecimiento());
                System.out.println("Libros: " + libros + "\n");
            }
        });
    }

    private void autoresVivosPorAno() {
        System.out.println("Ingrese el año:");
        String anio = teclado.nextLine();

        List<Autor> autoresVivos = repositorio.mostrarAutoresVivos(anio);

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores");
            return;
        }
        Map<String, List<String>> autoresConLibros = autoresVivos.stream()
                .collect(Collectors.groupingBy(
                        Autor::getNombre,
                        Collectors.mapping(a -> a.getLibro().getTitulo(), Collectors.toList())
                ));
        autoresConLibros.forEach((nombre, libros) -> {
            Autor autor = autoresVivos.stream()
                    .filter(a -> a.getNombre().equals(nombre))
                    .findFirst().orElse(null);
            if (autor != null) {
                System.out.println("Nombre: " + nombre);
                System.out.println("Fecha de nacimiento: " + autor.getFechaDeNacimiento());
                System.out.println("Fecha de fallecimiento: " + autor.getFechaDeFallecimiento());
                System.out.println("Libros: " + libros + "\n");
            }
        });
    }

    private void mostrarLibrosPorIdioma() {
        System.out.println("""
                idiomas disónibles:
                en: Ingles
                fr: Frances
                es: Español
                pt: Portugues
                """);
        var idiomaSelecionado = teclado.nextLine();
        try {
            List<Libro> libroPorIdioma = repositorio.findByIdiomas(Idioma.valueOf(idiomaSelecionado.toUpperCase()));
            libroPorIdioma.forEach(l -> System.out.println(
                    "Título: " + l.getTitulo() +
                            "\nAutor: " + l.getAutor().stream().map(Autor::getNombre).collect(Collectors.joining()) +
                            "\nIdioma: " + l.getIdiomas() +
                            "\nNúmero de descargas: " + l.getNumeroDeDescargas()));
        } catch (IllegalArgumentException e) {
            System.out.println("Idioma no disponible");
        }

    }

    private void top10descargas() {
        List<Libro> litaTop5 = repositorio.findTop5ByOrderByNumeroDeDescargasDesc();
        System.out.println("Libros más descargados");
        litaTop5.forEach(l -> System.out.println(
                "Libro: " + l.getTitulo() + " \nTotal de descargas: " + l.getNumeroDeDescargas()));
    }
}

