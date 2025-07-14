package Productos_cuidado_personal;

import Sistema_Hospitalario.RegistrarPaciente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame{
    private JPanel Principal;
    private JTextField usuariotextField1;
    private JButton accederButton;
    private JPasswordField passwordField1;

    public Login() {
        setContentPane(Principal); //Se establece el panel principal
        setTitle("Login"); //Titulo de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE); //Cierre del programa al cerrar ventana
        setSize(250,250); //Tamaño
        setLocationRelativeTo(null); //Se centra a la ventana
        setVisible(true); //Se hace visible

        //Accion para el boton acceder
        accederButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Se obtienen los datos de los campos
                String usuario = usuariotextField1.getText().trim();
                String contrasenia = String.valueOf(passwordField1.getPassword()).trim();

                //Se valida que los campos no esten vacios
                if (usuario.isEmpty() || contrasenia.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Llene todos los campos");
                    return;
                }

                //Conexion a la base de datos
                try (Connection cn = Conexion_DB.conectar()){
                    //Consulta para verificar las credenciales
                    String sql = "Select * from usuario where username = ? and password = ?";
                    PreparedStatement ps = cn.prepareStatement(sql); //Se prepara la consulta sql
                    //Se asignan los diferentes valores
                    ps.setString(1, usuario);
                    ps.setString(2, contrasenia);
                    ResultSet rs = ps.executeQuery(); //Se ejecuta la consulta

                    //Si las credenciales son correctas
                    if (rs.next()){
                        JOptionPane.showMessageDialog(null, "Acceso concedido");
                        new Registrar_Productos(); //Se muestra la ventana para registrar productos
                        dispose(); //se cierra la ventana actual
                    }else {
                        JOptionPane.showMessageDialog(null, "Acceso denegado");
                        //Se limpian los campos
                        usuariotextField1.setText("");
                        passwordField1.setText("");
                    }

                    //Manjeo de errores
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error de conexión");
                }
            }
        });
    }
}
