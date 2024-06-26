package com.empresa.javafx_mongo.controller;

import com.empresa.javafx_mongo.model.Datos;
import com.empresa.javafx_mongo.service.IService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.bson.types.ObjectId;


import java.util.List;
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
    private TextField txtNuevaCategoria;

    @FXML
    private ComboBox<String> ComboCatDelCat;

    @FXML
    private TextField txtSearch;

    @FXML
    private Pane headerPane;

    @FXML
    private ComboBox<String> filtroCategoria;

    private IService service;

    public HelloController(IService service) {
        this.service = service;
    }



    @FXML
    public void initialize() {
        try {
            headerPane.toFront();

            datosTable.setItems(service.getDatos());
            datosTable.getSelectionModel().clearSelection();
            comboCategoria.setItems(service.getOpciones());
            comboCategoria1.setItems(service.getOpciones());
            ComboCatDelCat.setItems(service.getOpciones());
            filtroCategoria.setItems(service.getOpciones());
            filtroCategoria.getItems().add(0,"Todas");

            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
            spinCantidad.setValueFactory(valueFactory);


            setTableListener(datosTable, txtNombreUpdate, txtPrecioUpdate, spinCantidad, comboCategoria1, txtNombreDelete, txtPrecioDelete, txtCantidadDelete, txtCategoriaDelete);
            setFiltroCategoriaListener();
        } catch (Exception e) {
            System.out.println("Error al inicializar: " + e.getMessage());
        }
    }

    // --------------------------------------------------------------------Evento click tabla
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
        String nombre = txtNombre.getText();
        String precio = txtPrecio.getText();
        String cantidad = txtCantidad.getText();
        String categoria = comboCategoria.getValue();

        // Verificar si alguno de los campos está vacío
        if (!service.camposValidos(nombre, precio, cantidad, categoria)) {
            // Mostrar una ventana de alerta si alguno de los campos está vacío
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Todos los campos deben estar llenos");
            alert.showAndWait();
        } else {
            // Verificar si el producto ya existe
            boolean productExists = service.productoExiste(nombre);

            if (productExists) {
                // Mostrar una ventana de alerta si el producto ya existe
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Ya tiene este producto en su inventario");
                alert.showAndWait();
            } else {
                // Crear el producto si no existe
                try {
                    service.crearProducto(nombre, precio, cantidad, categoria);
                    datosTable.setItems(service.getDatos());
                    txtNombre.setText("");
                    txtPrecio.setText("");
                    txtCantidad.setText("");
                    comboCategoria.setValue("");
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ventana de error");
                    alert.setHeaderText("Error al crear el producto");
                    alert.setContentText(e.getMessage());

                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    public void onBtnUpdateClick() { //-------------------------------------------Actualizar un producto
        String nombre = txtNombreUpdate.getText();
        String precio = txtPrecioUpdate.getText();
        Integer cantidad = spinCantidad.getValueFactory().getValue();
        String categoria = comboCategoria1.getValue();

        try {
            service.actualizarProducto(selectedId, nombre, precio, cantidad, categoria);
            datosTable.setItems(service.getDatos());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Producto actualizado");
            alert.setHeaderText(null);
            alert.setContentText("El producto ha sido actualizado con éxito");
            alert.showAndWait();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ventana de error");
            alert.setHeaderText("Error al actualizar el producto");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }
    @FXML
    public void onBtnDeleteClick() { //-------------------------------------------Borrar un producto
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de eliminación");
        alert.setHeaderText("Estás a punto de eliminar un producto");
        alert.setContentText("¿Estás seguro de que quieres continuar?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
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

    public void onBtnAgregarCategoriaClick() { //----------------------------------Agregar una categoria
        String categoria = txtNuevaCategoria.getText();

        if (categoria == null || categoria.isEmpty()) {
            // Mostrar una ventana emergente si la categoría está vacía
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("La categoría no puede estar vacía");
            alert.showAndWait();
        } else {
            service.crearCategoria(categoria);
            comboCategoria.setItems(service.getOpciones());
            comboCategoria1.setItems(service.getOpciones());
            ComboCatDelCat.setItems(service.getOpciones());

            txtNuevaCategoria.setText("");

            // Confirmación de creación exitosa
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Creación exitosa");
            alert.setHeaderText(null);
            alert.setContentText("La categoría ha sido creada con éxito");
            alert.showAndWait();
        }
    }

    public void onButtonClickBorrarCategoria() { //----------------------------------Borrar una categoria
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de eliminación");
        alert.setHeaderText("Estás a punto de eliminar una categoría");
        alert.setContentText("¿Estás seguro de que quieres continuar?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                String categoria = ComboCatDelCat.getValue();
                service.borrarCategoria(categoria);
                ComboCatDelCat.setItems(service.getOpciones());
                comboCategoria.setItems(service.getOpciones());
                comboCategoria1.setItems(service.getOpciones());
                datosTable.setItems(service.getDatos());

            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Ventana de error");
                errorAlert.setHeaderText("Error al eliminar la categoría");
                errorAlert.setContentText(e.getMessage());

                errorAlert.showAndWait();
            }
        }
    }

    @FXML
    public void onSearchKeyReleased(KeyEvent event) { //----------------------------------Buscar un producto
        String searchText = txtSearch.getText();
        List<Datos> filteredData = service.getDatosFiltrados(searchText);
        datosTable.setItems(FXCollections.observableArrayList(filteredData));
    }

    private void setFiltroCategoriaListener() { //----------------------------------Filtrar por categoría
        filtroCategoria.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue.equals("Todas")) {
                datosTable.setItems(service.getDatos());
            } else {
                datosTable.setItems(FXCollections.observableArrayList(service.getDatosPorCategoria(newValue)));
            }
        });
    }
}