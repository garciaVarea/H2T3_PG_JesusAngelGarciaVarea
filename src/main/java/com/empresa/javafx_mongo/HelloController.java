package com.empresa.javafx_mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.Document;

public class HelloController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TableView<Datos> datosTable;

    @FXML
    private TableColumn<Datos, String> nameColumn;

    @FXML
    private TableColumn<Datos, String> emailColumn;

    @FXML
    private TableColumn<Datos, String> IDColumn;

    private MongoClient mongoClient;
    private MongoDatabase database;

    @FXML
    protected void onHelloButtonClick() {
        mongoClient = MongoClients.create("mongodb+srv://admin:admin@cluster0.znfctkt.mongodb.net/");
        database = mongoClient.getDatabase("sample_mflix");
        MongoCollection<Document> collection = database.getCollection("comments");

        ObservableList<Datos> datosList = FXCollections.observableArrayList();
        for (Document doc : collection.find()) {
            datosList.add(new Datos(doc.getObjectId("_id").toString(), doc.getString("name"), doc.getString("email"), doc.getString("text"), doc.getDate("date")));
        }

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("_id"));

        datosTable.setItems(datosList);

        String name = nameTextField.getText();
        String email = emailTextField.getText();
        Datos selectedDatos = datosTable.getSelectionModel().getSelectedItem();
        String id = selectedDatos != null ? selectedDatos.get_id() : null;

        Document newDatos = new Document("name", name)
                .append("email", email)
                .append("id", id);

        collection.insertOne(newDatos);
    }
}