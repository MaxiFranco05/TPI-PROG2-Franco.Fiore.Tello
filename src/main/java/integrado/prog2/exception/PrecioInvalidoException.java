package integrado.prog2.exception;

public class PrecioInvalidoException extends RuntimeException {
    public PrecioInvalidoException(String message) {
        super(message);
    }
}