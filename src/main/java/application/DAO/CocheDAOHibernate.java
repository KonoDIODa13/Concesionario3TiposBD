package application.DAO;

import application.Conexion.ConexionHibernate;
import application.Model.Coche;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class CocheDAOHibernate implements CocheDAOImpl {
    private SessionFactory factory;
    private Session session;

    // Creo la conexión y recogo los datos de dicha conexión (el factory y la session).
    public CocheDAOHibernate() {
        ConexionHibernate.conexion();
        factory = ConexionHibernate.getFactory();
        session = ConexionHibernate.getSession();
    }

    /*
    Llamo de nuevo a la conexión que tiene un metodo para desconectarse de la BD.
    */
    @Override
    public void desconectarBD() {
        ConexionHibernate.desconectar();
    }

    /*
    Inserto un Coche con la sintaxis de Hibernate.
    */
    @Override
    public void insertarCoche(Coche coche) {
        session.beginTransaction();
        session.save(coche);
        session.getTransaction().commit();
    }

    /*
    Modifico un Coche con la sintaxis de Hibernate.
    */
    @Override
    public void modificarCoche(Coche coche) {
        session.beginTransaction();
        session.update(coche);
        session.getTransaction().commit();
    }

    /*
    Elimino un Coche con la sintaxis de Hibernate.
    */
    @Override
    public void eliminarCoche(Coche coche) {
        session.beginTransaction();
        session.delete(coche);
        session.getTransaction().commit();
    }

    /*
    Busco un Coche según la matricula (realizara un: select * from Coche where matricula="?").
    */
    @Override
    public Coche buscarCoche(int id) {
        Coche coche;
        coche = (Coche) session.get(Coche.class, id);
        return coche;
    }

    /*
    Recorro la tabla con createQuery(realiza un select * según la tabla que le pongas) y me lo devuelve como lista.
    */
    @Override
    public List<Coche> getCoches() {
        return session.createQuery("from Coche").getResultList();
    }

}
