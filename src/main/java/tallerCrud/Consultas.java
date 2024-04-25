package tallerCrud;
// @author amdtr

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Consultas {

    public static Conexion conexion = new Conexion();

    public void crearUsuario(Usuarios usuario) {

        String sql = "INSERT INTO usuarios(nombres, apellidos, dni) VALUES (?, ?, ?);";

        try {
            try ( //Insercion de usuario
                  PreparedStatement ps = conexion.conetandoBase().prepareStatement(sql)) {
                ps.setString(1, usuario.getNombre());
                ps.setString(2, usuario.getApellidos());
                ps.setString(3, usuario.getDni());
                ps.execute();
            }

            //Insercion de rol del usuario
            sql = "INSERT INTO usuariosroles(idusuario, idrol) VALUES ((SELECT id FROM usuarios WHERE estado = true ORDER BY id DESC limit 1), ?);";
            PreparedStatement pss = conexion.conetandoBase().prepareStatement(sql);
            pss.setInt(1, usuario.getRol().getId());
            pss.execute();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            conexion.cerrarBase();
        }
    }

    public void modificarUsuario(Usuarios usuarios) {
        String sql = "UPDATE usuarios SET nombres = ?, apellidos = ?, dni = ? WHERE id = ?;";
        try {
            try (PreparedStatement ps = conexion.conetandoBase().prepareStatement(sql)) {
                ps.setString(1, usuarios.getNombre());
                ps.setString(2, usuarios.getApellidos());
                ps.setString(3, usuarios.getDni());
                ps.setInt(4, usuarios.getId());
                ps.execute();
            }
            JOptionPane.showMessageDialog(null, "Usuario modificado con exito");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        } finally {
            conexion.cerrarBase();
        }

    }

    public void eliminarUsuarios(int id) {
        String sql = "UPDATE usuarios SET estado = false WHERE id = ?";

        try {
            try (PreparedStatement psU = conexion.conetandoBase().prepareStatement(sql)) {
                psU.setInt(1, id);
                psU.execute();
            }

            JOptionPane.showMessageDialog(null, "Usuario eliminado con exito");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        } finally {
            conexion.cerrarBase();
        }

    }

    public void mostrarDatos(JTable tabla) {
        String sql = "SELECT id, nombres, apellidos, dni FROM usuarios WHERE estado = true;";
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        modelo.addColumn("ID");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("DNI");
        //modelo.addColumn("Rol");

        try {
            Statement ps = conexion.conetandoBase().createStatement();
            ResultSet rs = ps.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString("id");
                String nombre = rs.getString("nombres");
                String apellidos = rs.getString("apellidos");
                String dni = rs.getString("dni");

                modelo.addRow(new Object[]{id, nombre, apellidos, dni});
                tabla.setModel(modelo);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        } finally {
            conexion.cerrarBase();
        }
    }

    public void selecionar(JTable tabla, Usuarios usuarios) {
        int fila = tabla.getSelectedRow();

        if (fila >= 0) {
            usuarios.getTxtId().setText(tabla.getValueAt(fila, 0).toString());
            usuarios.getTxtNombre().setText(tabla.getValueAt(fila, 1).toString());
            usuarios.getTxtApellidos().setText(tabla.getValueAt(fila, 2).toString());
            usuarios.getTxtDni().setText(tabla.getValueAt(fila, 3).toString());

        }
    }

}
