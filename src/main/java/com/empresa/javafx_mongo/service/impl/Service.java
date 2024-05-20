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

public class Service implements IService{
    private MongoClient mongoClient;
    private MongoDatabase database;

    public Service() {
        mongoClient = MongoClients.create("mongodb+srv://admin:admin@cluster0.znfctkt.mongodb.net/");
        database = mongoClient.getDatabase("Tienda");
    }

    public ObservableList<String> getOpciones() {
        MongoCollection<Document> collection = database.getCollection("Categorias");

        ObservableList<String> opcionesList = FXCollections.observableArrayList();
        for (Document doc : collection.find()) {
            opcionesList.add(doc.getString("categoria"));
        }

        return opcionesList;
    }

    @Override
    public ObservableList<Datos> getDatos() {
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
    @Override
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

    @Override
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
}