package crud;
// @author amdtr

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CDatos {

    public void CrearProducto(JTextField codigo, JTextField nombre, JTextField precio, JTextField unidades) {
        Conexion objetoConexion = new Conexion();
        String consulta = "INSERT INTO productos(codigo,nombre,precio,unidades)VALUES (?,?,?,?);";

        try {
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, codigo.getText());
            cs.setString(2, nombre.getText());
            cs.setDouble(3, Double.parseDouble(precio.getText()));
            cs.setInt(4, Integer.parseInt(unidades.getText()));
            cs.execute();
            System.out.println("se guardaron los datos");
        } catch (SQLException e) {
            System.out.println("No se guardaron los datos, error: " + e);
        } finally {
            objetoConexion.closedConexion();
        }

    }

    public void mostrarDatos(JTable tabla) {
        Conexion objetoConexion = new Conexion();
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        String consulta = "SELECT * FROM productos WHERE estado = true;";

        modelo.addColumn("ID");
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Disponibles");

        try {
            Statement st = objetoConexion.establecerConexion().createStatement();
            ResultSet rs = st.executeQuery(consulta);

            while (rs.next()) {
                String id = rs.getString("id");
                String codigo = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                String precio = rs.getString("precio");
                String unidades = rs.getString("unidades");

                modelo.addRow(new Object[]{id, codigo, nombre, precio, unidades});

                tabla.setModel(modelo);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error: " + e);
        } finally {
            objetoConexion.closedConexion();
        }

    }

    public void eliminarRegistro(JTextField txtId) {
        Conexion objetoConexion = new Conexion();
        String consulta = "UPDATE productos SET `estado` = false WHERE id = ?;";

        try {
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, txtId.getText());

            cs.execute();
            JOptionPane.showMessageDialog(null, "Registro eliminado");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error: " + e);
        } finally {
            objetoConexion.closedConexion();
        }

    }

    public void modificarRegistros(JTextField txtId, JTextField codigo, JTextField nombre, JTextField precio, JTextField unidades) {
        Conexion objetoConexion = new Conexion();
        String consulta = "UPDATE productos SET  codigo = ?, nombre = ?, precio = ?, unidades = ? WHERE id = ?;";

        try {
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, codigo.getText());
            cs.setString(2, nombre.getText());
            cs.setDouble(3, Double.parseDouble(precio.getText()));
            cs.setInt(4, Integer.parseInt(unidades.getText()));
            cs.setInt(5, Integer.parseInt(txtId.getText()));

            cs.execute();
            JOptionPane.showMessageDialog(null, "Datos modificados");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar los datos: " + e);
        } finally {
            objetoConexion.closedConexion();
        }

    }

    public void seleccionar(JTable tabla, JTextField txtId, JTextField codigo, JTextField nombre, JTextField precio, JTextField unidades) {

        int fila = tabla.getSelectedRow();

        if (fila >= 0) {
            txtId.setText(tabla.getValueAt(fila, 0).toString());
            codigo.setText(tabla.getValueAt(fila, 1).toString());
            nombre.setText(tabla.getValueAt(fila, 2).toString());
            precio.setText(tabla.getValueAt(fila, 3).toString());
            unidades.setText(tabla.getValueAt(fila, 4).toString());

        }

    }

    public void buscarRegistro(JTable tabla, JTextField txtId, JTextField codigo, JTextField nombre, JTextField precio, JTextField unidades) {
        Conexion objetoConexion = new Conexion();
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Disponibles");

        String consulta = "SELECT * FROM productos WHERE id = (?);";

        try {
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setInt(1, Integer.parseInt(txtId.getText()));
            cs.execute();

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String tcodigo = rs.getString("codigo");
                String tnombre = rs.getString("nombre");
                String tprecio = rs.getString("precio");
                String tunidades = rs.getString("unidades");
                modelo.addRow(new Object[]{id, tcodigo, tnombre, tprecio, tunidades});
                tabla.setModel(modelo);
                tabla.selectAll();
                seleccionar(tabla, txtId, codigo, nombre, precio, unidades);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);

        }finally{
            objetoConexion.closedConexion();
        }

    }

}
