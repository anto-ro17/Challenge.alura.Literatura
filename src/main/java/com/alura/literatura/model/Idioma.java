package com.alura.literatura.model;

public enum Idioma {
    EN("en"),
    FR("fr"),
    ES("es"),
    PT("pt");

    private String idiomaLibro;

    Idioma (String idiomaLibro){
        this.idiomaLibro = idiomaLibro;
    }

    public static Idioma fromString(String text) {
        for (Idioma Idioma : Idioma.values()) {
            if (Idioma.idiomaLibro.equalsIgnoreCase(text)) {
                return Idioma;
            }
        }
        throw new IllegalArgumentException("Ningun idioma encontrado: " + text);
    }
}
