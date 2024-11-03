package application.Controller;

import application.Utils.AlertUtils;
import application.Utils.CambioEscenas;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

public interface ControllerImpl {

    void limpiarCampos(ActionEvent event);

    void guardarCoche(ActionEvent event) throws SQLException;

    void modificarCoche(ActionEvent event) throws SQLException;

    void eliminarCoche(ActionEvent event) throws SQLException;

    void getCoche(MouseEvent event);

    void salir(ActionEvent event);
}