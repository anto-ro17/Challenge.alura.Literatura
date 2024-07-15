package com.alura.literatura.repository;

import com.alura.literatura.model.Autor;
import com.alura.literatura.model.Idioma;
import com.alura.literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibrosRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByIdiomas(Idioma idioma);

    List<Libro> findTop5ByOrderByNumeroDeDescargasDesc();
    @Query("SELECT l FROM Libro a JOIN a.autor l")
    List<Autor> mostrarAutores();

    @Query("SELECT l FROM Libro a JOIN a.autor l WHERE l.fechaDeNacimiento <= :anio AND l.fechaDeFallecimiento >= :anio")
    List<Autor> mostrarAutoresVivos(String anio);

}
