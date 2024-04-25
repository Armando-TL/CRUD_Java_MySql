package tallerCrud;
// @author amdtr

import javax.swing.JTextField;

public class Usuarios {

    private int id;
    private String nombre;
    private String apellidos;
    private String dni;
    private Rol rol;
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtApellidos, txtDni;

    public Usuarios(int id, String nombre, String apellidos, String dni, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.rol = rol;
    }

    public Usuarios(int id, String nombre, String apellidos, String dni) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
    }

    public Usuarios(String nombre, String apellidos, String dni, Rol rol) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.rol = rol;
    }
    
    // Constructor con los TXT
    public Usuarios(Rol rol, JTextField txtId, JTextField txtNombre, JTextField txtApellidos, JTextField txtDni) {
        this.rol = rol;
        this.txtId = txtId;
        this.txtNombre = txtNombre;
        this.txtApellidos = txtApellidos;
        this.txtDni = txtDni;
    }
    

    // Get de txt
    public JTextField getTxtId() {
        return txtId;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtApellidos() {
        return txtApellidos;
    }

    public JTextField getTxtDni() {
        return txtDni;
    }
    
    //Obtener valores de los campos

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDni() {
        return dni;
    }

    public Rol getRol() {
        return rol;
    }

    // Modificar valores
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

}
