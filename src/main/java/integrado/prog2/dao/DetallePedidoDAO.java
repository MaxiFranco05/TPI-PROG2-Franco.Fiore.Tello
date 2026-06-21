package integrado.prog2.dao;

import integrado.prog2.config.DatabaseConnection;
import integrado.prog2.entities.DetallePedido;
import integrado.prog2.entities.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetallePedidoDAO implements IDAO<DetallePedido> {

    private final ProductoDAO productoDAO = new ProductoDAO();

    @Override
    public void guardar(DetallePedido dp) {
        throw new UnsupportedOperationException("Usar guardarConTransaccion");
    }

    public void guardarConTransaccion(DetallePedido dp, Long idPedido, Connection conn) throws SQLException {
        String sql = "INSERT INTO detalle_pedido (cantidad, subtotal, id_producto, id_pedido, eliminado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, dp.getCantidad());
            ps.setDouble(2, dp.getSubtotal());
            ps.setLong(3, dp.getProducto().getId());
            ps.setLong(4, idPedido);
            ps.setBoolean(5, dp.isEliminado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) dp.setId(rs.getLong(1));
            }
        }
    }

    @Override
    public DetallePedido buscarPorId(Long id) {
        String sql = "SELECT * FROM detalle_pedido WHERE id = ? AND eliminado = false";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar detalle", e);
        }
        return null;
    }

    @Override
    public List<DetallePedido> listarTodos() {
        List<DetallePedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle_pedido WHERE eliminado = false";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar detalles", e);
        }
        return lista;
    }

    public List<DetallePedido> listarPorPedido(Long idPedido) {
        List<DetallePedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle_pedido WHERE id_pedido = ? AND eliminado = false";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar detalles por pedido", e);
        }
        return lista;
    }

    @Override
    public void actualizar(DetallePedido dp) {
        String sql = "UPDATE detalle_pedido SET cantidad = ?, subtotal = ?, id_producto = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dp.getCantidad());
            ps.setDouble(2, dp.getSubtotal());
            ps.setLong(3, dp.getProducto().getId());
            ps.setLong(4, dp.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar detalle", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "UPDATE detalle_pedido SET eliminado = true WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar detalle", e);
        }
    }

    private DetallePedido map(ResultSet rs) throws SQLException {
        Producto producto = productoDAO.buscarPorId(rs.getLong("id_producto"));
        DetallePedido dp = new DetallePedido(rs.getInt("cantidad"), producto);
        dp.setId(rs.getLong("id"));
        dp.setEliminado(rs.getBoolean("eliminado"));
        return dp;
    }
}