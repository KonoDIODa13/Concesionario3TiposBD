package application.DAO;

import application.Conexion.ConexionMYSQL;
import application.Model.Coche;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MYSQLDAO implements CocheDAOImpl {
    Connection conexion;

    public MYSQLDAO() {
        try {
            conexion = ConexionMYSQL.conectar();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    Llamo de nuevo a la conexión que tiene un metodo para desconectarse de la BD.
    */
    @Override
    public void desconectarBD() {
        try {
            ConexionMYSQL.desconectar(conexion);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    Inserto un Coche con la sintaxis de MYSQL.
    */
    @Override
    public void insertarCoche(Coche coche) throws SQLException {
        String insertVehiculo = "INSERT INTO coche (Matricula, Marca, Modelo, Tipo) VALUES (?, ?, ?, ?)";
        PreparedStatement sentencia = conexion.prepareStatement(insertVehiculo);

        sentencia.setString(1, coche.getMatricula());
        sentencia.setString(2, coche.getMarca());
        sentencia.setString(3, coche.getModelo());
        sentencia.setString(4, coche.getTipo());

        sentencia.executeUpdate();
    }

    /*
    Modifico un Coche con la sintaxis de MYSQL.
    */
    @Override
    public void modificarCoche(Coche coche) throws SQLException {
        String sql = "UPDATE coche SET Matricula= ?, Marca= ?, Modelo= ? ,Tipo= ? WHERE id= ?";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, coche.getMatricula());
        sentencia.setString(2, coche.getMarca());
        sentencia.setString(3, coche.getModelo());
        sentencia.setString(4, coche.getTipo());
        sentencia.setInt(5, coche.getId());
        System.out.println(coche.getId());
        //sentencia.executeUpdate();
        System.out.println(sentencia.executeUpdate());

    }

    /*
    Elimino un Coche con la sintaxis de MYSQL.
    */
    @Override
    public void eliminarCoche(Coche coche) throws SQLException {
        String sql = "DELETE FROM coche WHERE id=?";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, coche.getId());

        sentencia.executeUpdate();
    }

    /*
    Busco un Coche según la matricula (realizara un: select * from Coche where matricula="?").
    */


    @Override
    public List<Coche> getCoches() throws SQLException {
        List<Coche> coches = new ArrayList<>();
        String sql = "select * FROM coche";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            int id = resultado.getInt(1);
            String matricula = resultado.getString(2);
            String marca = resultado.getString(3);
            String modelo = resultado.getString(4);
            String tipo = resultado.getString(5);
            Coche coche = new Coche(matricula, marca, modelo, tipo);
            coches.add(coche);
            System.out.println(coche.getId());
        }
        return coches;
    }

    public int getId(String matricula) throws SQLException {
        int id = -1;

        // consigo el id del vehiculo cuya matricula paso por parametro.
        String sql = "SELECT Id FROM Vehiculo WHERE Matricula= ?";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, matricula);
        ResultSet resultado = sentencia.executeQuery();

        while (resultado.next()) {
            id = resultado.getInt(1);
        }

        return id;
    }

}
