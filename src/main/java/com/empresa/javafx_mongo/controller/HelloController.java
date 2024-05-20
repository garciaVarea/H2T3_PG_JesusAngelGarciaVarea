package com.empresa.javafx_mongo.controller;

import com.empresa.javafx_mongo.model.Datos;
import com.empresa.javafx_mongo.service.impl.Service;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.bson.Document;
import org.bson.types.Decimal128;

public class HelloController {

    @FXML
    private TableView<Datos> datosTable;

    @FXML
    private TableColumn<Datos, String> idColumn;

    @FXML
    private TableColumn<Datos, String> nombreColumn;

    @FXML
    private TableColumn<Datos, Decimal128> precioColumn;

    @FXML
    private TableColumn<Datos, Integer> cantidadColumn;

    @FXML
    private ComboBox<String> categoriaComboBox;

    private MongoClient mongoClient;
    private MongoDatabase database;

    @FXML
    public void initialize() {
        Service service = new Service();
        datosTable.setItems(service.getDatos());
    }
}