package com.empresa.javafx_mongo.controller;

import com.empresa.javafx_mongo.service.IService;
import com.empresa.javafx_mongo.service.impl.Service;
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
import javafx.scene.paint.Color;
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
            IService service = new Service(username, password);
            errorMessage.setText(""); // Clear error message

            // Redirige a HelloApplication
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/empresa/javafx_mongo/hello-view.fxml"));
            HelloController helloController = new HelloController(service);
            fxmlLoader.setController(helloController);
            Parent mainView = fxmlLoader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(mainView));
            stage.show();

        } catch (MongoException e) {
            errorMessage.setText("Error al conectar a la base de datos: " + e.getMessage());
        } catch (IOException e) {
            errorMessage.setText("Usuario incorrecto" + e.getMessage());
            errorMessage.setTextFill(Color.RED);
        }
    }
}