package entities;

import enums.Estado;
import enums.FormaPago;
import exceptions.PedidoInvalidoException;
import interfaces.Calculable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends Base implements Calculable {
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private List<DetallePedido> detalles;

    public Pedido(Usuario usuario, FormaPago formaPago) {
        if (usuario == null) throw new PedidoInvalidoException("El pedido debe tener un usuario.");
        this.usuario = usuario;
        this.formaPago = formaPago;
        this.total = 0.0;
        this.fecha = LocalDate.from(getCreatedAt());
        this.estado = Estado.PENDIENTE;
        this.detalles = new ArrayList<>();
    }

    public void addDetallePedido(int cantidad, Double precio, Producto producto) {
        DetallePedido detalle = new DetallePedido(cantidad, producto);
        detalles.add(detalle);
        calcularTotal();
    }

    public DetallePedido findeDetallePedidoByProducto(Producto producto) {
        for (DetallePedido d : detalles) {
            if (d.getProducto().equals(producto)) return d;
        }
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido detalle = findeDetallePedidoByProducto(producto);
        if (detalle != null) {
            detalles.remove(detalle);
            calcularTotal();
        }
    }

    @Override
    public void calcularTotal() {
        Double acumTotal = 0.0;
        try {
            for (DetallePedido detalle : detalles) {
                acumTotal += detalle.getSubtotal();
            }
        } catch (Exception e) {
            System.out.println("Error al calcular total: " + e.getMessage());
        }
        this.total = acumTotal;
    }

    public LocalDate getFecha() { return fecha; }
    public Estado getEstado() { return estado; }
    public Double getTotal() { return total; }
    public FormaPago getFormaPago() { return formaPago; }
    public Usuario getUsuario() { return usuario; }
    public List<DetallePedido> getDetalles() { return detalles; }

    public void setEstado(Estado estado) { this.estado = estado; }
    public void setFormaPago(FormaPago formaPago) { this.formaPago = formaPago; }

    @Override
    public String toString() {
        return "Pedido [ID=" + getId() + "]" +
                " | Usuario: " + usuario.getNombre() + " " + usuario.getApellido() +
                " | Estado: " + estado +
                " | FormaPago: " + formaPago +
                " | Total: $" + total +
                " | Fecha: " + fecha;
    }
}