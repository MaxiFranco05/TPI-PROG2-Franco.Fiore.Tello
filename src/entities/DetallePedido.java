package entities;

public class DetallePedido extends Base{
    private int cantidad;
    private Double subtotal;
    private Producto producto;


    public Double getSubtotal() {
        return subtotal;
    }
}
