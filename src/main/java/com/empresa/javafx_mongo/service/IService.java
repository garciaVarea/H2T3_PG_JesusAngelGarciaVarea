package com.empresa.javafx_mongo.service;

import com.empresa.javafx_mongo.model.Datos;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import org.bson.types.ObjectId;

import java.util.List;

public interface IService {
    ObservableList<Datos> getDatos();
    ObservableList<String> getOpciones();
    void crearProducto(String nombre, String precio, String cantidad, String categoria) throws NumberFormatException;
    void actualizarProducto(ObjectId id, String nombre, String precioStr, Integer cantidad, String categoria) throws NumberFormatException;
    void eliminarProducto(ObjectId id);
    void crearCategoria(String categoria);
    void borrarCategoria(String categoria);
    List<Datos> getDatosFiltrados(String searchText);
    List<Datos> getDatosPorCategoria(String categoria);
    boolean productoExiste(String nombre);
    boolean camposValidos(String nombre, String precio, String cantidad, String categoria);
}