package entities;

import java.util.ArrayList;
import java.util.List;

public class Categoria extends Base{
    private String nombre;
    private String descripcion;
    private List<Producto> productos;

    public Categoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.productos = new ArrayList<>();
    }

    public Categoria(String nombre){
        this(nombre, "SIN DESCRIPCIÓN");
    }

    public void addProductoToProductos(){

    }
}
