package com.empresa.javafx_mongo.controller;

import com.empresa.javafx_mongo.model.Datos;
import com.empresa.javafx_mongo.service.impl.Service;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;

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
    private TextField txtNombreUpdate;

    @FXML
    private TextField txtPrecioUpdate;

    @FXML
    private Spinner<Integer> spinCantidad;

    @FXML
    private ComboBox<String> comboCategoria1;

    private ObjectId selectedId;

    @FXML
    public void initialize() {
        Service service = new Service();
        datosTable.setItems(service.getDatos());
        comboCategoria.setItems(service.getOpciones());
        comboCategoria1.setItems(service.getOpciones());

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        spinCantidad.setValueFactory(valueFactory);

        // Add a listener to the table
        datosTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Datos datos = datosTable.getSelectionModel().getSelectedItem();

                selectedId = datos.get_id();
                txtNombreUpdate.setText(datos.getNombre());
                txtPrecioUpdate.setText(datos.getPrecio().toString());
                spinCantidad.getValueFactory().setValue(datos.getCantidad());
                comboCategoria1.setValue(datos.getCategoria());
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
}