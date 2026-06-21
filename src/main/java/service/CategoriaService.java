package service;

import dao.CategoriaDAO;
import entities.Categoria;

import java.util.List;

public class CategoriaService {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    public void guardar(Categoria c) {
        categoriaDAO.guardar(c);
    }

    public Categoria buscarPorId(Long id) {
        return categoriaDAO.buscarPorId(id);
    }

    public List<Categoria> listarTodos() {
        return categoriaDAO.listarTodos();
    }

    public void actualizar(Categoria c) {
        categoriaDAO.actualizar(c);
    }

    public void eliminar(Long id) {
        categoriaDAO.eliminar(id);
    }
}