package com.empresa.javafx_mongo.service;

import com.empresa.javafx_mongo.model.Datos;
import javafx.collections.ObservableList;

import org.bson.types.ObjectId;

public interface IService {
    ObservableList<Datos> getDatos();
    ObservableList<String> getOpciones();
    void crearProducto(String nombre, String precio, String cantidad, String categoria) throws NumberFormatException;
    void actualizarProducto(ObjectId id, String nombre, String precioStr, Integer cantidad, String categoria) throws NumberFormatException;
    void eliminarProducto(ObjectId id);
}