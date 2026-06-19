package entities;

import enums.Rol;
import exceptions.UsuarioInvalidoException;
import java.util.ArrayList;
import java.util.List;

public class Usuario extends Base {
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasena;
    private Rol rol;
    private List<Pedido> pedidos;

    public Usuario(String nombre, String apellido, String mail, String celular, String contrasena, Rol rol) {
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setMail(mail);
        this.setCelular(celular);
        this.contrasena = contrasena;
        this.rol = (rol != null) ? rol : Rol.USUARIO;
        this.pedidos = new ArrayList<>();
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getMail() { return mail; }
    public String getCelular() { return celular; }
    public String getContrasena() { return contrasena; }
    public Rol getRol() { return rol; }
    public List<Pedido> getPedidos() { return pedidos; }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = (nombre != null && !nombre.isBlank()) ? nombre : "Sin nombre";
    }

    public void setApellido(String apellido) {
        this.apellido = (apellido != null && !apellido.isBlank()) ? apellido : "Sin apellido";
    }

    public void setMail(String mail) {
        if (mail != null && !mail.isBlank()) {
            this.mail = mail;
        } else {
            throw new UsuarioInvalidoException("El mail no puede estar vacío.");
        }
    }

    public void setCelular(String celular) {
        this.celular = (celular != null && !celular.isBlank()) ? celular : "Sin celular";
    }

    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public void setRol(Rol rol) { this.rol = rol; }

    // Métodos
    public void addPedido(Pedido pedido) {
        if (pedido != null && !pedidos.contains(pedido)) {
            pedidos.add(pedido);
        }
    }

    @Override
    public String toString() {
        return "Usuario [ID=" + getId() + "] " + nombre + " " + apellido +
                " | Mail: " + mail +
                " | Celular: " + celular +
                " | Rol: " + rol +
                " | Pedidos: " + pedidos.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Usuario)) return false;
        Usuario otro = (Usuario) obj;
        return this.mail.equalsIgnoreCase(otro.mail);
    }
}