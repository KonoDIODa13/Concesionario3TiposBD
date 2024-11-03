package application.CRUD;

import application.Model.Coche;
import application.Utils.AlertUtils;

import java.util.List;

public interface CocheCRUDImpl {
    void desconectar();

    List<Coche> getCoches();

    public boolean insertarCoche(List<String> campos);

    public boolean modificarCoche(List<String> campos, Coche antiguoCoche);

    public void eliminarCoche(Coche coche);
}
