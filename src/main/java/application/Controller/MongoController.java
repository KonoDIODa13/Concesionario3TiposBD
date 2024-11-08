package application.Controller;

import application.CRUD.HibernateCRUD;
import application.CRUD.MongoCRUD;
import application.Model.Coche;
import application.Model.Tipo;
import application.Utils.AlertUtils;
import application.Utils.CambioEscenas;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MongoController implements Initializable, ControllerImpl{
    public AnchorPane concesionario;
    // Implemento la clase Initializable para cargar tanto la tabla y el combox con los datos.
    @FXML
    private ComboBox<Tipo> cbTipo;

    @FXML
    private TextField tfMatricula, tfModelo, tfMarca;

    @FXML
    private TableView<Coche> tvCoches;

    @FXML
    private TableColumn<Coche, String> tcMatricula, tcMarca, tcModelo, tcTipo;

    MongoCRUD crud;
    List<Coche> coches;
    Coche cocheSeleccionado = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Este metodo se realiza antes de cargar el componente. En el, cargo la tabla y el combox.
        cargarTabla();
        cargarCB();
        tcMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        tcMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tcModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }

    public MongoController() {
        // Creo un constructor en el que instancio el crud para acceder a la funcionalidad del crud.
        crud = new MongoCRUD();
    }


    @FXML
    public void limpiarCampos(ActionEvent event) {
        // metodo para limpiar tanto los campos como el combox.
        tfMatricula.setText("");
        tfMarca.setText("");
        tfModelo.setText("");
        cargarCB();
        cocheSeleccionado = null;

    }

    @FXML
    public void guardarCoche(ActionEvent event) {
        /*
        Este metodo guarda en base de datos el coche creado con los campos de texto. para ello, recojo los datos
        y los paso a insertarCoche del cocheCrud
         */
        List<String> campos = new ArrayList<>();
        String matricula = tfMatricula.getText();
        String marca = tfMarca.getText();
        String modelo = tfModelo.getText();
        String tipo;
        if (cbTipo.getValue() != null) {
            tipo = cbTipo.getValue().toString();
        } else {
            tipo = null;
        }
        insertarCampo(campos, matricula);
        insertarCampo(campos, marca);
        insertarCampo(campos, modelo);
        insertarCampo(campos, tipo);
        if (crud.insertarCoche(campos)) {
            AlertUtils.mostrarConfirmacion("Coche creado correctamente.");
            cargarTabla();
            limpiarCampos(event);
        }
    }

    @FXML
    public void modificarCoche(ActionEvent event) {
        /*
        Este metodo modifica el coche seleccionado. Para ello, recogo los datos de los textField y el coche seleccionado.
        Después, llamo a la función de crud para modificar el coche que requiere de una lista de String de campos
        y el coche seleccionado.
         */
        if (cocheSeleccionado != null) {
            List<String> campos = new ArrayList<>();
            String matricula = tfMatricula.getText();
            String marca = tfMarca.getText();
            String modelo = tfModelo.getText();
            String tipo;
            if (cbTipo.getValue() != null) {
                tipo = cbTipo.getValue().toString();
            } else {
                tipo = null;
            }

            insertarCampo(campos, matricula);
            insertarCampo(campos, marca);
            insertarCampo(campos, modelo);
            insertarCampo(campos, tipo);

            if (crud.modificarCoche(campos, cocheSeleccionado)) {
                AlertUtils.mostrarConfirmacion("Coche modificado correctamente.");
                limpiarCampos(event);
                cargarTabla();
            }
        } else {
            AlertUtils.mostrarError("Seleccione primero el coche");
        }
    }

    @FXML
    public void eliminarCoche(ActionEvent event) {
        /*
        Este metodo elimina el coche. Para ello, consigo el coche seleccionado y pregunto al usuario si realmente
        quiere borrar el coche seleccionado.
         */
        if (cocheSeleccionado != null) {
            int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea borrar el coche?", "Confirmación", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                crud.eliminarCoche(cocheSeleccionado);
                limpiarCampos(event);
                cargarTabla();
            }
        } else {
            AlertUtils.mostrarError("Seleccione primero el coche");
        }
    }

    @FXML
    public void getCoche(MouseEvent event) {
        // Metodo que selecciona el coche en la tabla.
        try {
            cocheSeleccionado = tvCoches.getSelectionModel().getSelectedItem();
            cargarData();
        } catch (NullPointerException e) {
            AlertUtils.mostrarError("No has seleccionado ningun dato.\n");
        }

    }

    @FXML
    public void salir(ActionEvent event) {
        // Metodo para salir del programa.
        crud.desconectar();
        CambioEscenas.cambioEscena("inicioConcesionario.fxml", concesionario);
    }

    public void cargarTabla() {
        /*
         para cargar la tabla, primero limpio la tabla (para evitar problemas)
         y cojo del crud los coches que me lo devuelve como una lista y la vuelco en la tabla.
         Para que se vean correctamente, le pongo a cada tableColumn el valor de la propiedad que tiene que coger.
         */
        tvCoches.getItems().clear();
        coches = crud.getCoches();

        tvCoches.setItems(FXCollections.observableList(coches));
    }

    public void cargarCB() {
        // Al igual que el cargarTabla, cargo los valores del enum Tipo.
        cbTipo.getItems().clear();
        cbTipo.getItems().addAll(Tipo.values());
    }


    public void cargarData() {
        // Este metodo carga los datos del coche seleccionado.
        tfMatricula.setText(cocheSeleccionado.getMatricula());
        tfMarca.setText(cocheSeleccionado.getMarca());
        tfModelo.setText(cocheSeleccionado.getModelo());
        Tipo tipoCoche = null;
        for (Tipo tipo : Tipo.values()) {
            if (tipo.toString().equals(cocheSeleccionado.getTipo())) {
                tipoCoche = tipo;
            }
        }
        cbTipo.setValue(tipoCoche);
    }

    public void insertarCampo(List<String> campos, String campo) {
        // Metodo para insertar en un array de Strings el campo que me interesa meter.
        campos.add(campo);
    }
}
