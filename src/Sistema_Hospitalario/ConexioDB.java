package Sistema_Hospitalario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Clase para la conexion con la base de datos
public class ConexioDB {
    //URL de conexion al servidor de MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/Sistema_hospitalario";
    private static final String USER = "root"; //Usuario
    private static final String PASSWORD = "1234"; //Contraseña

    //Metodo que devuelve una conexion a la base de datos
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
