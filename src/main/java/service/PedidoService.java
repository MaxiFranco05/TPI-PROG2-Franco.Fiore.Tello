package service;

import config.DatabaseConnection;
import dao.DetallePedidoDAO;
import dao.PedidoDAO;
import entities.DetallePedido;
import entities.Pedido;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PedidoService {
    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final DetallePedidoDAO detallePedidoDAO = new DetallePedidoDAO();

    public void guardar(Pedido p) {
        pedidoDAO.guardar(p);
    }

    public Pedido buscarPorId(Long id) {
        return pedidoDAO.buscarPorId(id);
    }

    public List<Pedido> listarTodos() {
        return pedidoDAO.listarTodos();
    }

    public void actualizar(Pedido p) {
        pedidoDAO.actualizar(p);
    }

    public void eliminar(Long id) {
        pedidoDAO.eliminar(id);
    }

    public void guardarConDetalles(Pedido pedido) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            pedidoDAO.guardarConTransaccion(pedido, conn);
            for (DetallePedido detalle : pedido.getDetalles()) {
                detallePedidoDAO.guardarConTransaccion(detalle, pedido.getId(), conn);
            }

            pedido.calcularTotal();
            pedidoDAO.actualizarTotal(pedido.getId(), pedido.getTotal(), conn);

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) {
                    throw new RuntimeException("Error al hacer rollback", ex);
                }
            }
            throw new RuntimeException("Error al guardar pedido con detalles: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) {
                    System.err.println("Error cerrando conexión: " + e.getMessage());
                }
            }
        }
    }
}