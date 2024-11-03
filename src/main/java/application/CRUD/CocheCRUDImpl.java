package application.CRUD;

import application.Model.Coche;
import application.Utils.AlertUtils;

import java.sql.SQLException;
import java.util.List;

public interface CocheCRUDImpl {
    void desconectar();

    List<Coche> getCoches() throws SQLException;

    public boolean insertarCoche(List<String> campos) throws SQLException;

    public boolean modificarCoche(List<String> campos, Coche antiguoCoche) throws SQLException;

    public void eliminarCoche(Coche coche) throws SQLException;
}
