package Productos_cuidado_personal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Buscar_Productos extends JFrame{
    private JPanel Principal;
    private JTextField codigotextField1;
    private JTextArea vistatextArea1;
    private JButton buscarButton;
    private JButton registrarProductosButton;
    private JButton cerrarButton;

    public Buscar_Productos() {
        setContentPane(Principal);
        setTitle("Buscar Productos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,350);
        setLocationRelativeTo(null);
        setVisible(true);

        //Accion para el boton buscar
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Se obtiene el codigo ingresado
                String codigo = codigotextField1.getText().trim();

                //Se valida que el campo no este vacio
                if (codigo.isEmpty()){
                    JOptionPane.showMessageDialog(null, "El campo debe estar completo");
                    return;
                }

                //Conexion con la base de datos
                try (Connection cn = Conexion_DB.conectar()){
                    //Consulta para buscar el producto
                    String sql = "select * from producto where codigo_producto = ?";
                    java.sql.PreparedStatement ps = cn.prepareStatement(sql);
                    ps.setString(1, codigo);
                    java.sql.ResultSet rs = ps.executeQuery();

                    if (rs.next()){
                        //Se obtiene la informacion del producto
                        String historial = "Código: "+rs.getString("codigo_producto") +
                                "\nNombre: "+rs.getString("nombre")
                                + "\nDescripción: "+ rs.getString("descripcion")+
                                "\nPrecio: "+rs.getDouble("precio")
                                + "\nCantidad: " + rs.getInt("cantidad") +
                                "\nCategoría: "+ rs.getString("categoria");

                        //Se muestra la informacion en el area de texto
                        vistatextArea1.setText(historial);
                        vistatextArea1.setEditable(false); //Se modifica el area de texto para que no sea editable
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "No hay registros del producto");
                    }

                    //Manejo de errores
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al buscar el producto");
                }
            }
        });

        //Accion para el boton registrar
        registrarProductosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Registrar_Productos(); //Se muestra la ventana de Registrar Productos
                dispose(); //Se cierra la venta actual
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
