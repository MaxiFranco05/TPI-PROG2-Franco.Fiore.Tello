package integrado.prog2.service;

import integrado.prog2.dao.ProductoDAO;
import integrado.prog2.entities.Producto;

import java.util.List;

public class ProductoService {
    private final ProductoDAO productoDAO = new ProductoDAO();

    public void guardar(Producto p) {
        productoDAO.guardar(p);
    }

    public Producto buscarPorId(Long id) {
        return productoDAO.buscarPorId(id);
    }

    public List<Producto> listarTodos() {
        return productoDAO.listarTodos();
    }

    public List<Producto> listarPorCategoria(Long idCategoria) {
        return productoDAO.listarPorCategoria(idCategoria);
    }

    public void actualizar(Producto p) {
        productoDAO.actualizar(p);
    }

    public void eliminar(Long id) {
        productoDAO.eliminar(id);
    }
}