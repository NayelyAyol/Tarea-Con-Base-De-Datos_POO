package Productos_cuidado_personal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Clase para la conexion a la base de datos
public class Conexion_DB {
    private static final String URL = "jdbc:mysql://localhost:3306/productos_cuidado_personal"; //Conexion al servidor de MySQL
    private static final String USER = "root"; //Usuario
    private static final String PASSWORD = "1234"; //Contraseña

    //Metodo estatico que devuelve una conexion a la base de datos
    public static Connection conectar() throws SQLException {
        //Se utiliza DriverManager para establecer y retornar la conexión con los parámetros definidos
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
