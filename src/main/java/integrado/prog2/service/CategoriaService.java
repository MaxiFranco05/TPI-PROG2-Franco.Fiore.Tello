package integrado.prog2.service;

import integrado.prog2.dao.CategoriaDAO;
import integrado.prog2.entities.Categoria;

import java.util.List;

public class CategoriaService {
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    public boolean existePorNombre(String nombre) {
        return categoriaDAO.existePorNombre(nombre);
    }

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