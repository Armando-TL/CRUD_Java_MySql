package crud;
// @author amdtr

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    Connection conectar = null;

    String user = "root";
    String pass = "";
    String base = "E192";
    String ip = "localhost";
    String puerto = "3306";
    String url = "jdbc:mysql://" + ip + ":" + puerto + "/" + base;

    public Connection establecerConexion() {

        try {
            conectar = DriverManager.getConnection(url, user, pass);
            System.out.println("Se conecto a la base de datos");
        } catch (SQLException e) {
            System.out.println("Error al conecta la base de datos. Error : " + e);
        }

        return conectar;
    }

    public void closedConexion() {
        try {
            if (conectar != null && !conectar.isClosed()) {
                conectar.close();
                System.out.println("Conexion cerrada");
            }
        } catch (SQLException e) {
            System.out.println("No se pudo cerrar la conexion");
        }
    }
}
