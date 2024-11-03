package application.Controller;

import application.Utils.AlertUtils;
import application.Utils.CambioEscenas;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public interface ControllerImpl {

    void limpiarCampos(ActionEvent event);

    void guardarCoche(ActionEvent event);

    void modificarCoche(ActionEvent event);

    void eliminarCoche(ActionEvent event);

    void getCoche(MouseEvent event);

    void salir(ActionEvent event);
}