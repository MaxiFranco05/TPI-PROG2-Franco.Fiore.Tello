package dao;

import config.DatabaseConnection;
import entities.Pedido;
import entities.DetallePedido;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO implements IDAO<Pedido> {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final DetallePedidoDAO detallePedidoDAO = new DetallePedidoDAO();

    @Override
    public void guardar(Pedido p) {
        String sql = "INSERT INTO pedido (fecha, estado, total, forma_pago, id_usuario, eliminado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, Date.valueOf(p.getFecha()));
            ps.setString(2, p.getEstado().name());
            ps.setDouble(3, p.getTotal());
            ps.setString(4, p.getFormaPago().name());
            ps.setLong(5, p.getUsuario().getId());
            ps.setBoolean(6, p.isEliminado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) p.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar pedido", e);
        }
    }

    @Override
    public Pedido buscarPorId(Long id) {
        String sql = "SELECT * FROM pedido WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar pedido", e);
        }
        return null;
    }

    @Override
    public List<Pedido> listarTodos() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido WHERE eliminado = false";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar pedidos", e);
        }
        return lista;
    }

    @Override
    public void actualizar(Pedido p) {
        String sql = "UPDATE pedido SET fecha = ?, estado = ?, total = ?, forma_pago = ?, id_usuario = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(p.getFecha()));
            ps.setString(2, p.getEstado().name());
            ps.setDouble(3, p.getTotal());
            ps.setString(4, p.getFormaPago().name());
            ps.setLong(5, p.getUsuario().getId());
            ps.setLong(6, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar pedido", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "UPDATE pedido SET eliminado = true WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar pedido", e);
        }
    }

    public void guardarConTransaccion(Pedido p, Connection conn) throws SQLException {
        String sql = "INSERT INTO pedido (fecha, estado, total, forma_pago, id_usuario, eliminado) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, Date.valueOf(p.getFecha()));
            ps.setString(2, p.getEstado().name());
            ps.setDouble(3, p.getTotal());
            ps.setString(4, p.getFormaPago().name());
            ps.setLong(5, p.getUsuario().getId());
            ps.setBoolean(6, p.isEliminado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) p.setId(rs.getLong(1));
            }
        }
    }

    public void actualizarTotal(Long id, Double total, Connection conn) throws SQLException {
        String sql = "UPDATE pedido SET total = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, total);
            ps.setLong(2, id);
            ps.executeUpdate();
        }
    }

    private Pedido map(ResultSet rs) throws SQLException {
        Usuario usuario = usuarioDAO.buscarPorId(rs.getLong("id_usuario"));
        Pedido p = new Pedido(usuario, FormaPago.valueOf(rs.getString("forma_pago")));
        p.setId(rs.getLong("id"));
        p.setEliminado(rs.getBoolean("eliminado"));
        p.setEstado(Estado.valueOf(rs.getString("estado")));
        // Cargar detalles
        List<DetallePedido> detalles = detallePedidoDAO.listarPorPedido(rs.getLong("id"));
        for (DetallePedido d : detalles) {
            p.addDetallePedido(d.getCantidad(), d.getSubtotal() / d.getCantidad(), d.getProducto());
        }
        return p;
    }
}