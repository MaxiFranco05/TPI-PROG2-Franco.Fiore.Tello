package entities;

import exceptions.StockInvalidoException;
import exceptions.ProductoInvalidoException;

public class DetallePedido extends Base {
    private int cantidad;
    private Double subtotal;
    private Producto producto;

    public DetallePedido(int cantidad, Producto producto) {
        if (cantidad <= 0) throw new StockInvalidoException("La cantidad debe ser mayor a 0.");

        if (producto == null) throw new ProductoInvalidoException("El producto no puede ser nulo.");


        this.cantidad = cantidad;
        this.producto = producto;
        this.subtotal = cantidad * producto.getPrecio();
    }
    public int getCantidad() { return cantidad; }
    public Double getSubtotal() { return subtotal; }
    public Producto getProducto() { return producto; }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) throw new StockInvalidoException("La cantidad debe ser mayor a 0.");
        this.cantidad = cantidad;
        this.subtotal = cantidad * producto.getPrecio();
    }

    @Override
    public String toString() {
        return "  DetallePedido [ID=" + getId() + "] " +
                producto.getNombre() +
                " x" + cantidad +
                " | Subtotal: $" + subtotal;
    }

}

