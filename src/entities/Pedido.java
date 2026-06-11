package entities;

import enums.Estado;
import enums.FormaPago;
import interfaces.Calculable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Pedido extends Base implements Calculable {
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private List<DetallePedido> detalles;

    public Pedido(FormaPago formaPago) {
        this.formaPago = formaPago;
        this.total = 0.0;
        this.fecha = LocalDate.from(getCreatedAt());
        this.estado = Estado.PENDIENTE;
        this.detalles = new ArrayList<>();
    }

    @Override
    public void calcularTotal() {
        Double acumTotal = 0.0;
        /*AGREGAR TRY/CATCH*/
        if (!detalles.isEmpty() /*AGREGAR .ANY(NULL)*/){
            for (DetallePedido detalle : detalles) {
                acumTotal+= detalle.getSubtotal();
            }
        }
        this.total = acumTotal;
    }
}
