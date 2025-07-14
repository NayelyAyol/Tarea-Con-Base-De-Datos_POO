package Productos_cuidado_personal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Registrar_Productos extends JFrame{
    private JTextField codigotextField1;
    private JTextField nombretextField2;
    private JTextField descripciontextField3;
    private JTextField preciotextField4;
    private JTextField categoriatextField5;
    private JTextField cantidadtextField6;
    private JButton registrarButton;
    private JPanel Principal;
    private JButton buscarButton;

    public Registrar_Productos(){
        setContentPane(Principal);
        setTitle("Buscar Productos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(250,400);
        setLocationRelativeTo(null);
        setVisible(true);

        //Accion para el boton registrar
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Se toman los datos ingresados por el usuario
                String codigo = codigotextField1.getText().trim();
                String nombre = nombretextField2.getText().trim();
                String descripcion = descripciontextField3.getText().trim();
                String precio = preciotextField4.getText().trim();
                String cantidad = cantidadtextField6.getText().trim();
                String categoria = categoriatextField5.getText().trim();

                //Se valida que los campos no esten vacios
                if (codigo.isEmpty() || nombre.isEmpty() || descripcion.isEmpty() || precio.isEmpty() ||
                        cantidad.isEmpty() || categoria.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Todos los campos deben ser completados");
                    return;
                }

                //Conexion a la base de datos
                try (Connection cn = Conexion_DB.conectar()){
                    //Sentencia sql para insertar un nuevo producto
                    String sql = "insert into producto (codigo_producto, nombre, descripcion, precio, cantidad, categoria) values (?,?,?,?,?,?)";
                    PreparedStatement ps = cn.prepareStatement(sql); //Se prepara la sentencia
                    ps.setString(1, codigo);
                    ps.setString(2, nombre);
                    ps.setString(3, descripcion);
                    ps.setDouble(4, Double.parseDouble(precio));
                    ps.setDouble(5, Double.parseDouble(cantidad));
                    ps.setString(6, categoria);
                    ps.executeUpdate(); //Se ejecuta la sentencia

                    JOptionPane.showMessageDialog(null, "Registro exitoso");
                    //Se limpian los campos
                    codigotextField1.setText("");
                    nombretextField2.setText("");
                    descripciontextField3.setText("");
                    preciotextField4.setText("");
                    cantidadtextField6.setText("");
                    categoriatextField5.setText("");

                    //Manejo de errores
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al registrar el producto");
                }

            }
        });

        //Accion para el boton Buscar
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Buscar_Productos(); //Se muestra la ventana de Buscar Productos
                dispose(); //Se cierra la ventana actual
            }
        });
    }
}
