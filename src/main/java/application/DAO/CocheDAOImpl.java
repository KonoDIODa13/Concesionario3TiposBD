package application.DAO;

import application.Model.Coche;

import java.util.List;

/*
    Creo la interfaz para que el CocheDAO realize solo las operaciones que esten en la interfaz
 */

public interface CocheDAOImpl {

    void desconectarBD();

    void insertarCoche(Coche coche);


    List<Coche> getCoches();

    void modificarCoche(Coche coche);

    void eliminarCoche(Coche coche);

    //Coche buscarCoche(int id);
}
