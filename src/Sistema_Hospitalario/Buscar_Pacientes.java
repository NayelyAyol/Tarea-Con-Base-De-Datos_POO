package Sistema_Hospitalario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Buscar_Pacientes extends JFrame{
    private JPanel Principal;
    private JTextField cedulatextField1;
    private JTextArea vistatextArea1;
    private JButton volverAlRegistroButton;
    private JButton cerrarButton;
    private JButton buscarButton;

    public Buscar_Pacientes(){
        setContentPane(Principal);
        setTitle("Buscar Pacientes");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,350);
        setLocationRelativeTo(null);
        setVisible(true);

        //Accion para el boton buscar
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Se obtiene el dato ingresado por el usuario
                String cedula = cedulatextField1.getText().trim();

                //Se valida que el campo no este vacio
                if (cedula.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Los campos deben estar completos");
                    return;
                }

                //Conexion con la base
                try (Connection cn = ConexioDB.conectar()){
                    //Cunsulta SQL
                    String sql = "Select * from paciente where cedula = ?";
                    java.sql.PreparedStatement ps = cn.prepareStatement(sql); //Se prepara la consulta
                    ps.setString(1, cedula); //Se asigna la cedula al parametro
                    java.sql.ResultSet res= ps.executeQuery(); //Se ejecuta la consulta

                    //Si se encuentra la paciente se guarda su informacion en una variable
                    if (res.next()){
                        String registro = "Cédula: " + res.getString("cedula") +
                                "\nHistorial Clínico: " + res.getInt("n_historial_clinico") +
                                "\nNombre: " + res.getString("nombre") +
                                "\nApellido: " + res.getString("apellido") +
                                "\nTeléfono: " + res.getString("telefono") +
                                "\nEdad: " + res.getInt("edad") +
                                "\nEnfermedad: " + res.getString("descripcion_enfermedad");

                        vistatextArea1.setText(registro); //Se muestra la informacion
                        vistatextArea1.setEditable(false); //Convierte al area de texto en no editable
                    }else {
                        JOptionPane.showMessageDialog(null, "No existen registros del paciente");
                        vistatextArea1.setText("");
                    }

                    //Manejo de errores SQL
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al buscar paciente");
                }
            }
        });

        //Accion para el boton volver al registro
        volverAlRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrarPaciente(); //Se abre la ventana del Registro de pacientes
                dispose(); //Se cierra la ventana actual
            }
        });

        //Accion para el boton cerrar
        cerrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login(); //Se abre la ventana de Login
                dispose(); //Se cierra la ventana actual
            }
        });
    }
}
