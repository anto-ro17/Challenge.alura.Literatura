package com.alura.literatura.service;

public interface IAPIDataConverter {
    <T> T obtenerDatos(String json, Class<T> clase);
}
