package com.empresa.javafx_mongo.controller;

import com.empresa.javafx_mongo.model.Datos;
import com.empresa.javafx_mongo.service.impl.Service;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

import java.util.Optional;

public class HelloController {

    @FXML
    private TableView<Datos> datosTable;

    @FXML
    private ComboBox<String> comboCategoria;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtNombreUpdate;

    @FXML
    private TextField txtPrecioUpdate;

    @FXML
    private Spinner<Integer> spinCantidad;

    @FXML
    private ComboBox<String> comboCategoria1;

    private ObjectId selectedId;

    @FXML
    private TextField txtNombreDelete;

    @FXML
    private TextField txtPrecioDelete;

    @FXML
    private TextField txtCantidadDelete;

    @FXML
    private TextField txtCategoriaDelete;

    @FXML
    public void initialize() {
        Service service = new Service();
        datosTable.setItems(service.getDatos());
        comboCategoria.setItems(service.getOpciones());
        comboCategoria1.setItems(service.getOpciones());

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        spinCantidad.setValueFactory(valueFactory);

        // Set the table listener using the new method in HelloController
        setTableListener(datosTable, txtNombreUpdate, txtPrecioUpdate, spinCantidad, comboCategoria1, txtNombreDelete, txtPrecioDelete, txtCantidadDelete, txtCategoriaDelete);
    }

    public void setTableListener(TableView<Datos> datosTable, TextField txtNombreUpdate, TextField txtPrecioUpdate, Spinner<Integer> spinCantidadUpdate, ComboBox<String> comboCategoriaUpdate, TextField txtNombreDelete, TextField txtPrecioDelete, TextField txtCantidadDelete, TextField txtCategoriaDelete) {
        datosTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Datos datos = datosTable.getSelectionModel().getSelectedItem();

                selectedId = datos.get_id();

                txtNombreUpdate.setText(datos.getNombre());
                txtPrecioUpdate.setText(datos.getPrecio().toString());
                spinCantidadUpdate.getValueFactory().setValue(datos.getCantidad());
                comboCategoriaUpdate.setValue(datos.getCategoria());

                txtNombreDelete.setText(datos.getNombre());
                txtPrecioDelete.setText(datos.getPrecio().toString());
                txtCantidadDelete.setText(String.valueOf(datos.getCantidad()));
                txtCategoriaDelete.setText(datos.getCategoria());
            }
        });
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

    @FXML
    public void onBtnUpdateClick() {
        Service service = new Service();
        String nombre = txtNombreUpdate.getText();
        String precio = txtPrecioUpdate.getText();
        Integer cantidad = spinCantidad.getValueFactory().getValue();
        String categoria = comboCategoria1.getValue();

        try {
            service.actualizarProducto(selectedId, nombre, precio, cantidad, categoria);
            datosTable.setItems(service.getDatos());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ventana de error");
            alert.setHeaderText("Error al actualizar el producto");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }
    @FXML
    public void onBtnDeleteClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de eliminación");
        alert.setHeaderText("Estás a punto de eliminar un producto");
        alert.setContentText("¿Estás seguro de que quieres continuar?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Service service = new Service();
            try {
                service.eliminarProducto(selectedId);
                datosTable.setItems(service.getDatos());
                txtNombreDelete.setText("");
                txtPrecioDelete.setText("");
                txtCantidadDelete.setText("");
                txtCategoriaDelete.setText("");
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Ventana de error");
                errorAlert.setHeaderText("Error al eliminar el producto");
                errorAlert.setContentText(e.getMessage());

                errorAlert.showAndWait();
            }
        }
    }
}