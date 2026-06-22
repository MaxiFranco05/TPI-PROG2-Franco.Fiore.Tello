package integrado.prog2.dao;

import integrado.prog2.config.DatabaseConnection;
import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO implements IDAO<Producto> {

    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    @Override
    public void guardar(Producto p) {
        String sql = "INSERT INTO producto (nombre, precio, descripcion, stock, imagen, disponible, id_categoria, eliminado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setString(3, p.getDescripcion());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getImagen());
            ps.setBoolean(6, p.getDisponible());
            ps.setLong(7, p.getCategoria().getId());
            ps.setBoolean(8, p.isEliminado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) p.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar producto", e);
        }
    }

    @Override
    public Producto buscarPorId(Long id) {
        String sql = "SELECT * FROM producto WHERE id = ? AND eliminado = false";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar producto", e);
        }
        return null;
    }

    @Override
    public List<Producto> listarTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE eliminado = false";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar productos", e);
        }
        return lista;
    }

    public List<Producto> listarPorCategoria(Long idCategoria) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE id_categoria = ? AND eliminado = false";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, idCategoria);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar productos por categoría", e);
        }
        return lista;
    }

    @Override
    public void actualizar(Producto p) {
        String sql = "UPDATE producto SET nombre = ?, precio = ?, descripcion = ?, stock = ?, imagen = ?, disponible = ?, id_categoria = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setString(3, p.getDescripcion());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getImagen());
            ps.setBoolean(6, p.getDisponible());
            ps.setLong(7, p.getCategoria().getId());
            ps.setLong(8, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar producto", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "UPDATE producto SET eliminado = true WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar producto", e);
        }
    }

    public void guardarConTransaccion(Producto p, Connection conn) throws SQLException {
        String sql = "INSERT INTO producto (nombre, precio, descripcion, stock, imagen, disponible, id_categoria, eliminado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setString(3, p.getDescripcion());
            ps.setInt(4, p.getStock());
            ps.setString(5, p.getImagen());
            ps.setBoolean(6, p.getDisponible());
            ps.setLong(7, p.getCategoria().getId());
            ps.setBoolean(8, p.isEliminado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) p.setId(rs.getLong(1));
            }
        }
    }

    private Producto map(ResultSet rs) throws SQLException {
        Categoria categoria = categoriaDAO.buscarPorId(rs.getLong("id_categoria"));
        Producto p = new Producto(
                rs.getString("nombre"),
                rs.getDouble("precio"),
                rs.getString("descripcion"),
                rs.getInt("stock"),
                rs.getString("imagen"),
                categoria
        );
        p.setId(rs.getLong("id"));
        p.setEliminado(rs.getBoolean("eliminado"));
        return p;
    }
}