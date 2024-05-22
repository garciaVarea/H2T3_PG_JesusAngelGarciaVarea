package com.empresa.javafx_mongo.controller;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessage;

    @FXML
    protected void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            MongoClientURI uri = new MongoClientURI("mongodb+srv://" + username + ":" + password + "@cluster0.znfctkt.mongodb.net/");
            MongoClient mongoClient = new MongoClient(uri);
            mongoClient.close();
            errorMessage.setText(""); // Clear error message

            // Redirige a HelloApplication
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/empresa/javafx_mongo/hello-view.fxml"));
            Parent mainView = fxmlLoader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(mainView));
            stage.show();

        } catch (MongoException e) {
            errorMessage.setText("Error al conectar a la base de datos: " + e.getMessage());
        } catch (IOException e) {
            errorMessage.setText("Error al cargar la vista: " + e.getMessage());
        }
    }
}