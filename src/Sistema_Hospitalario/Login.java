package Sistema_Hospitalario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame{
    private JPanel Principal;
    private JTextField usuariotextField1;
    private JButton ingresarButton;
    private JPasswordField passwordField1;

    public Login(){
        setContentPane(Principal); //Se establece el panel principal
        setTitle("Login"); //Titulo de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE); //Cierre del programa al cerrar ventana
        setSize(250,250); //Tamaño
        setLocationRelativeTo(null); //Se centra a la ventana
        setVisible(true); //Hace visible a la ventana

        //Accion para el boton ingresar
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //se obtiene el texto de los campos
                String usuario = usuariotextField1.getText().trim();
                String password = String.valueOf(passwordField1.getPassword()).trim();

                //Se valida que los campos no esten vacios
                if (usuario.isEmpty() || password.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Los campos deben ser completados");
                    return;
                }

                //Conexion y validacion en la base de datos
                try (Connection cn = ConexioDB.conectar()){
                    //Consulta SQL para verificar las credenciales
                    String sql = "SELECT * FROM USUARIO WHERE username = ? AND password = ?";
                    PreparedStatement ps = cn.prepareStatement(sql); //Se prepara la consulta con parametros
                    //Se reemplazan los ? con usuario y password segun corresponda
                    ps.setString(1, usuario);
                    ps.setString(2, password);
                    ResultSet rs = ps.executeQuery(); //Se ejecuta la consulta y guarda el resultado

                    //Si el usuario es encontrado, se da paso a la ventana de Registro Pacientes
                    if (rs.next()){
                        JOptionPane.showMessageDialog(null, "Acceso concedido");
                        new RegistrarPaciente();
                        dispose();
                    }else {
                        JOptionPane.showMessageDialog(null, "Acceso denegado");
                        usuariotextField1.setText("");
                        passwordField1.setText("");
                    }

                } catch (SQLException ex) { //Manejo de errores en SQL
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error en la conexión");
                }
            }
        });
    }
}
