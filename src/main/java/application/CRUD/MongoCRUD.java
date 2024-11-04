package application.CRUD;


import application.DAO.MongoDAO;
import application.Model.Coche;
import application.Utils.AlertUtils;

import java.util.List;

public class MongoCRUD implements CocheCRUDImpl {

    MongoDAO dao;
    List<Coche> coches;

    public MongoCRUD() {
        // Al crear el CocheCrud, instancio el CocheDao para realizarlo solo una vez y realizar la conexión a la bd
        dao = new MongoDAO();
        dao.conectarBD();
    }

    public void desconectar() {
        // Metodo para desconectar la bd.
        dao.desconectarBD();
    }

    public List<Coche> getCoches() {
        /*
        consigo la lista de coches que me devuelve la funcion del CocheDAO de getCoches() y la devuelvo.
        También, igualo la lista de coches que tengo general para poder realizar comprobaciones en otros metodos.
         */

        coches = dao.getCoches();
        coches.forEach(Coche::toString);
        return coches;
    }

    public boolean insertarCoche(List<String> campos) {
        /*
        Metodo de insertar un coche. Primero compruebo si los campos son correctos. Si son correctos, creo un coche con
        esos datos para después comprobar si el coche que quiero insertar existe ya en la bd
        (ya que la lista es un reflejo de la base de datos) o si existe un registro en la bd que tenga la matricula
        que queremos añadir. Por último, llamará al metodo de CochesDAO de insertarCoche.
         */
        if (comprobaciones(campos)) return false;
        int id = creaIDCoche();
        Coche coche = new Coche(id, campos.get(0), campos.get(1), campos.get(2), campos.get(3));
        if (coches.contains(coche)) {
            AlertUtils.mostrarError("el coche ya esta en la bd.");
            return false;
        }
        if (coches.stream().anyMatch(car -> car.getMatricula().equalsIgnoreCase(coche.getMatricula()))) {
            AlertUtils.mostrarError("El coche con la matricula: " + coche.getMatricula() + " ya existe en la bd.");
            return false;
        }
        dao.insertarCoche(coche);
        return true;
    }

    public boolean modificarCoche(List<String> campos, Coche antiguoCoche) {
        /*
        Este metodo, al igual que el de insertar coche, compruebo los campos que voy a meter y si el coche que voy a
        meter esta ya en la bd. Realizao una comprobación si existe un coche con la matrícula que se intenta modificar
        salvando la del propio vehiculo que queremos modificar. Por último, llamamos al la función modificarCoche.
         */
        if (comprobaciones(campos)) return false;

        antiguoCoche.setMatricula(campos.get(0));
        antiguoCoche.setMarca(campos.get(1));
        antiguoCoche.setModelo(campos.get(2));
        antiguoCoche.setTipo(campos.get(3));

        dao.modificarCoche(antiguoCoche);
        return true;
    }

    public void eliminarCoche(Coche coche) {
        // En este metodo, eliminamos el coche que recibimos por parametro llamando a la función eliminarCoche.
        dao.eliminarCoche(coche);
        AlertUtils.mostrarConfirmacion("Coche eliminado correctamente.");
    }

    public boolean comprobaciones(List<String> campos) {
        // Función para comprobar todos si los campos pasan las comprobaciones.
        return compruebaCampo(campos.get(0), "matricula") ||
                compruebaCampo(campos.get(1), "marca") ||
                compruebaCampo(campos.get(2), "modelo") ||
                compruebaCampo(campos.get(3), "tipo");
    }

    public boolean compruebaCampo(String contenido, String campo) {
        // En esta función comprobamos si el campo está vacio. Si lo está, mostramos un error.
        boolean bool = false;
        if (contenido.isEmpty()) {
            AlertUtils.mostrarError("El campo " + campo + " está vacio");
            bool = true;
        }
        return bool;
    }

    public int creaIDCoche() {
        return getCoches().size() + 1;
    }
}
