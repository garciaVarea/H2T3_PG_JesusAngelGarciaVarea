package com.empresa.javafx_mongo.service.impl;

import com.empresa.javafx_mongo.model.Datos;
import com.empresa.javafx_mongo.service.IService;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;
import org.bson.types.Decimal128;

public class Service implements IService{
    private MongoClient mongoClient;
    private MongoDatabase database;

    public Service() {
        mongoClient = MongoClients.create("mongodb+srv://admin:admin@cluster0.znfctkt.mongodb.net/");
        database = mongoClient.getDatabase("Tienda");
    }
    @Override
    public ObservableList<Datos> getDatos() {
        MongoCollection<Document> collection = database.getCollection("Productos");

        ObservableList<Datos> datosList = FXCollections.observableArrayList();
        for (Document doc : collection.find()) {
            Integer cantidad = doc.getInteger("cantidad");
            if (cantidad == null) {
                cantidad = 0; // or any default value you want
            }
            datosList.add(new Datos(
                    doc.getObjectId("_id"),
                    doc.getString("nombre"),
                    doc.get("precio", Decimal128.class),
                    cantidad,
                    doc.getString("categoria")
            ));
        }

        return datosList;
    }
}