package application.Controller;

import application.Utils.CambioEscenas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;

public class InicioController {

    public AnchorPane paneInicio;

    @FXML
    public void btnHibernate(ActionEvent event) {
        CambioEscenas.cambioEscena("concesionarioHibernate.fxml", paneInicio);
    }

    @FXML
    public void btnMongo(ActionEvent event) {
        CambioEscenas.cambioEscena("concesionarioMongo.fxml", paneInicio);
    }

    @FXML
    public void btnMysql(ActionEvent event) {
        CambioEscenas.cambioEscena("concesionarioMYSQL.fxml", paneInicio);

    }

    @FXML
    public void btnSalir(ActionEvent event) {
        int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            ((Stage) paneInicio.getScene().getWindow()).close();
        }
    }

}
