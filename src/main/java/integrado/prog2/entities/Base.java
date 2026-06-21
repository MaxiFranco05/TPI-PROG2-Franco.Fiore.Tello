package integrado.prog2.entities;

import java.time.LocalDateTime;

public abstract class Base {
    private Long id;
    private boolean eliminado = false;
    private LocalDateTime createdAt;

    public Base(){
        this.id = null;
        createdAt = LocalDateTime.now();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}