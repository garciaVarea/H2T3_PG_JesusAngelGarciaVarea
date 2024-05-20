package com.empresa.javafx_mongo.controller;

import com.empresa.javafx_mongo.model.Datos;
import com.empresa.javafx_mongo.service.impl.Service;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private ComboBox<String> comboCategoria;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtCantidad;


    @FXML
    public void initialize() {
        Service service = new Service();
        datosTable.setItems(service.getDatos());
        comboCategoria.setItems(service.getOpciones());
    }
    @FXML
    public void onBtnCreateClick() {
        Service service = new Service();
        String nombre = txtNombre.getText();
        String precio = txtPrecio.getText();
        String cantidad = txtCantidad.getText();
        String categoria = comboCategoria.getValue();

        try {
            service.crearProducto(nombre, precio, cantidad, categoria);
            datosTable.setItems(service.getDatos());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ventana de error");
            alert.setHeaderText("Error al crear el producto");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }
}