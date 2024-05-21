package com.empresa.javafx_mongo.service.impl;

import com.empresa.javafx_mongo.model.Datos;
import com.empresa.javafx_mongo.service.IService;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;


public class Service implements IService{
    private MongoClient mongoClient;
    private MongoDatabase database;

    public Service() {//--------------------------------------------------conexión a la base de datos
        mongoClient = MongoClients.create("mongodb+srv://admin:admin@cluster0.znfctkt.mongodb.net/");
        database = mongoClient.getDatabase("Tienda");
    }

    public ObservableList<String> getOpciones() {//--------------------------------------------------obtener las categorías
        MongoCollection<Document> collection = database.getCollection("Categorias");

        ObservableList<String> opcionesList = FXCollections.observableArrayList();
        for (Document doc : collection.find()) {
            opcionesList.add(doc.getString("categoria"));
        }

        return opcionesList;
    }

    @Override
    public ObservableList<Datos> getDatos() {//--------------------------------------------------obtener los datos para tabla
        MongoCollection<Document> collection = database.getCollection("Productos");

        ObservableList<Datos> datosList = FXCollections.observableArrayList();
        try {
            for (Document doc : collection.find()) {
                Integer cantidad = doc.getInteger("cantidad");
                if (cantidad == null) {
                    cantidad = 0;
                }
                datosList.add(new Datos(
                        doc.getObjectId("_id"),
                        doc.getString("nombre"),
                        doc.get("precio", Decimal128.class),
                        cantidad,
                        doc.getString("categoria")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error al obtener los datos: " + e.getMessage());
            e.printStackTrace();
        }

        return datosList;
    }

    @Override //--------------------------------------------------crear un producto
    public void crearProducto(String nombre, String precioStr, String cantidadStr, String categoria) throws NumberFormatException {
        MongoCollection<Document> collection = database.getCollection("Productos");

        Decimal128 precio = null;
        int cantidad = 0;

        try {
            precio = Decimal128.parse(precioStr);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Error en el precio: " + e.getMessage());
        }

        try {
            cantidad = Integer.parseInt(cantidadStr);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Error en la cantidad: " + e.getMessage());
        }

        Document doc = new Document()
                .append("nombre", nombre)
                .append("precio", precio)
                .append("cantidad", cantidad)
                .append("categoria", categoria);

        collection.insertOne(doc);
    }

    public void crearCategoria(String categoria) {
        MongoCollection<Document> collection = database.getCollection("Categorias");

        Document doc = new Document()
                .append("categoria", categoria);

        collection.insertOne(doc);
    }

    @Override //--------------------------------------------------actualizar un producto
    public void actualizarProducto(ObjectId id, String nombre, String precioStr, Integer cantidad, String categoria) throws NumberFormatException {
        MongoCollection<Document> collection = database.getCollection("Productos");

        Decimal128 precio = null;

        try {
            precio = Decimal128.parse(precioStr);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Error en el precio: " + e.getMessage());
        }

        Document doc = new Document()
                .append("nombre", nombre)
                .append("precio", precio)
                .append("cantidad", cantidad)
                .append("categoria", categoria);

        collection.updateOne(Filters.eq("_id", id), new Document("$set", doc));
    }

    public void eliminarProducto(ObjectId id) { //--------------------------------------------------eliminar un producto
        MongoCollection<Document> collection = database.getCollection("Productos");
        collection.deleteOne(Filters.eq("_id", id));
    }

    public void eliminarProductosPorCategoria(String categoria) {
        MongoCollection<Document> collection = database.getCollection("Productos");
        collection.deleteMany(Filters.eq("categoria", categoria));
    }

    public void borrarCategoria(String categoria) {
        eliminarProductosPorCategoria(categoria);

        MongoCollection<Document> collection = database.getCollection("Categorias");
        collection.deleteOne(Filters.eq("categoria", categoria));
    }

    @Override
    public List<Datos> getDatosFiltrados(String searchText) {//--------------------------------------------------filtrar los datos
        List<Datos> allDatos = getDatos();
        return allDatos.stream()
                .filter(dato -> dato.getNombre().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

}