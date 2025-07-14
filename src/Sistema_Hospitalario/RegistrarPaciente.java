package Sistema_Hospitalario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrarPaciente extends JFrame{
    private JPanel Principal;
    private JTextField historialtextField1;
    private JTextField nombretextField1;
    private JTextField apellidotextField1;
    private JTextField tltextField2;
    private JTextField edadtextField3;
    private JTextField descripciontextField4;
    private JButton registrarButton;
    private JButton buscarButton;
    private JTextField cedulatextField1;

    public RegistrarPaciente(){
        setContentPane(Principal);
        setTitle("Registrar Paciente");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(250,550);
        setLocationRelativeTo(null);
        setVisible(true);

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Se obtienen los valores ingresados por el usuario
                String cedula = cedulatextField1.getText();
                String historial = historialtextField1.getText();
                String nombre = nombretextField1.getText();
                String apellido = apellidotextField1.getText();
                String telefono = tltextField2.getText();
                String edad = edadtextField3.getText();
                String descripcion = descripciontextField4.getText();

                //Se validan los campos no esten vacios
                if (cedula.isEmpty() || historial.isEmpty() || nombre.isEmpty() ||
                        apellido.isEmpty() || telefono.isEmpty() || edad.isEmpty() || descripcion.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Los campos no deben estar vacíos");
                    return;
                }

                //Se establece conexion con la BD
                try (Connection cn = ConexioDB.conectar()){
                    //Sentencia SQL para insertar un nuevo paciente
                    String sql = "Insert into paciente (cedula, n_historial_clinico, nombre, apellido, telefono, edad, descripcion_enfermedad) values (?,?,?,?,?,?,?)";
                    PreparedStatement ps = cn.prepareStatement(sql); //Se prepara la consulta
                    //Se asignan las variables
                    ps.setString(1,cedula);
                    ps.setInt(2, Integer.parseInt(historial));
                    ps.setString(3, nombre);
                    ps.setString(4, apellido);
                    ps.setString(5, telefono);
                    ps.setInt(6, Integer.parseInt(edad));
                    ps.setString(7, descripcion);
                    ps.executeUpdate(); //Se ejecuta la sentencia

                    JOptionPane.showMessageDialog(null, "Paciente registrado con éxito");
                    //Se limpian los campos del formulario
                    cedulatextField1.setText("");
                    historialtextField1.setText("");
                    nombretextField1.setText("");
                    apellidotextField1.setText("");
                    tltextField2.setText("");
                    edadtextField3.setText("");
                    descripciontextField4.setText("");

                    //Se manejan errores SQL
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al registrar el paciente");
                }

            }
        });

        //Accion para el boton buscar
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Buscar_Pacientes(); //Se abre la ventana de Buscar Pacientes
                dispose(); //Se cierra la ventana actual
            }
        });
    }
}
