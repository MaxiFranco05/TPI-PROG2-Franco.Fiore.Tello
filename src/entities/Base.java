package entities;

import java.time.LocalDateTime;

public abstract class Base {
    private static Long acumId = 0L;
    private Long id;
    private boolean eliminado = false;
    private LocalDateTime createdAt;

    public Base(){
        this.id = acumId++;
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
