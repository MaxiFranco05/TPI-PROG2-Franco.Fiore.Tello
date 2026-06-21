package integrado.prog2.dao;

import java.util.List;

public interface IDAO<T> {
    void guardar(T entidad);
    T buscarPorId(Long id);
    List<T> listarTodos();
    void actualizar(T entidad);
    void eliminar(Long id);
}