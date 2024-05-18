package com.empresa.javafx_mongo;

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
    protected void onHelloButtonClick() {
        mongoClient = MongoClients.create("mongodb+srv://admin:admin@cluster0.znfctkt.mongodb.net/");
        database = mongoClient.getDatabase("Tienda");
        MongoCollection<Document> collection = database.getCollection("Productos");

        ObservableList<Datos> datosList = FXCollections.observableArrayList();
        for (Document doc : collection.find()) {
            datosList.add(new Datos(
                    doc.getObjectId("_id"),
                    doc.getString("nombre"),
                    doc.get("precio", Decimal128.class),
                    doc.getInteger("cantidad"),
                    doc.getString("categoria")
            ));
        }

        datosTable.setItems(datosList);
    }
}