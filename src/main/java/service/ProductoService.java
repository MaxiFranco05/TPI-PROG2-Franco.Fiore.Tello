package service;

import dao.ProductoDAO;
import entities.Producto;

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

    public void actualizar(Producto p) {
        productoDAO.actualizar(p);
    }

    public void eliminar(Long id) {
        productoDAO.eliminar(id);
    }
}