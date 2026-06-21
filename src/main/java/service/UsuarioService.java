package service;

import dao.UsuarioDAO;
import entities.Usuario;

import java.util.List;

public class UsuarioService {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void guardar(Usuario u) {
        usuarioDAO.guardar(u);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioDAO.buscarPorId(id);
    }

    public List<Usuario> listarTodos() {
        return usuarioDAO.listarTodos();
    }

    public void actualizar(Usuario u) {
        usuarioDAO.actualizar(u);
    }

    public void eliminar(Long id) {
        usuarioDAO.eliminar(id);
    }
}