package com.empresa.javafx_mongo;

import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

public class Datos {
    private ObjectId _id;
    private String nombre;
    private Decimal128 precio;
    private int cantidad;
    private String categoria;

    public Datos(ObjectId _id, String nombre, Decimal128 precio, int cantidad, String categoria) {
        this._id = _id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria;
    }

    // getters and setters
    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Decimal128 getPrecio() {
        return precio;
    }

    public void setPrecio(Decimal128 precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}