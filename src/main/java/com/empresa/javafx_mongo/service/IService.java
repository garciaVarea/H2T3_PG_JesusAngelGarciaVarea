package com.empresa.javafx_mongo.service;

import com.empresa.javafx_mongo.model.Datos;
import javafx.collections.ObservableList;
import org.bson.types.Decimal128;

public interface IService {
    ObservableList<Datos> getDatos();
    ObservableList<String> getOpciones();
    void crearProducto(String nombre, String precio, String cantidad, String categoria) throws NumberFormatException;
}