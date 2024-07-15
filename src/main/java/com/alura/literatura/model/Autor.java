package com.alura.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   private String nombre;
   private String fechaDeNacimiento;
   private String fechaDeFallecimiento;
   @ManyToOne
   private Libro libro;

   public Autor(){}

   public Autor(DatosAutor datosAutor){
       this.nombre = datosAutor.nombre();
       this.fechaDeNacimiento = datosAutor.fechaDeNacimiento();
       this.fechaDeFallecimiento = datosAutor.fechaDefallecimiento();
   }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(String fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return
                "Id: " + id + '\'' +
                ", Nombre: " + nombre + '\'' +
                ", Año de nacimiento: '" + fechaDeNacimiento + '\'' +
                ", Año de fallecimiento ='" + fechaDeFallecimiento + '\'' +
                ", libro: " + libro;
    }
}
