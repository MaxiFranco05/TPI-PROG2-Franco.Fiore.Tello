package integrado.prog2.dao;

import integrado.prog2.config.DatabaseConnection;
import integrado.prog2.entities.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO implements IDAO<Categoria> {

    @Override
    public void guardar(Categoria c) {
        String sql = "INSERT INTO categoria (nombre, descripcion, eliminado) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());
            ps.setBoolean(3, c.isEliminado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar categoría", e);
        }
    }

    @Override
    public Categoria buscarPorId(Long id) {
        String sql = "SELECT * FROM categoria WHERE id = ? AND eliminado = false";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar categoría", e);
        }
        return null;
    }

    @Override
    public List<Categoria> listarTodos() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoria WHERE eliminado = false";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(map(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar categorías", e);
        }
        return lista;
    }

    @Override
    public void actualizar(Categoria c) {
        String sql = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());
            ps.setLong(3, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar categoría", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "UPDATE categoria SET eliminado = true WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar categoría", e);
        }
    }

    private Categoria map(ResultSet rs) throws SQLException {
        Categoria c = new Categoria(rs.getString("nombre"), rs.getString("descripcion"));
        c.setId(rs.getLong("id"));
        c.setEliminado(rs.getBoolean("eliminado"));
        return c;
    }
}