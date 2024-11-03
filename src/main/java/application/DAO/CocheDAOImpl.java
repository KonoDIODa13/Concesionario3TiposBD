package application.DAO;

import application.Model.Coche;

import java.sql.SQLException;
import java.util.List;

/*
    Creo la interfaz para que el CocheDAO realize solo las operaciones que esten en la interfaz
 */

public interface CocheDAOImpl {

    void desconectarBD();

    void insertarCoche(Coche coche) throws SQLException;


    List<Coche> getCoches() throws SQLException;

    void modificarCoche(Coche coche) throws SQLException;

    void eliminarCoche(Coche coche) throws SQLException;

    //Coche buscarCoche(int id);
}
