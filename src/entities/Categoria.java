package entities;

import java.util.ArrayList;
import java.util.List;

public class Categoria extends Base{
    private String nombre;
    private String descripcion;
    private List<Producto> productos;

    public Categoria(String nombre, String descripcion) {
        this.setNombre(nombre);
        this.setDescripcion(descripcion);
        this.productos = new ArrayList<>();
    }

    public Categoria(String nombre){
        this(nombre, "SIN DESCRIPCIÓN");
    }

    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public List<Producto> getProductos() { return productos; }



    public void setNombre(String nombre) {
        if(nombre!=null && !nombre.isBlank()){
            this.nombre = nombre;
        }
        else{
            this.nombre = "No tiene nombre";
        }
    }
    public void setDescripcion(String descripcion){
        if(descripcion!=null && !descripcion.isBlank()){
            this.descripcion = descripcion;
        }
        else{
            this.descripcion = "No tiene descripcion";
        }
    }



    public void addProductoToProductos(Producto producto){
        if(producto!=null && !productos.contains(producto)){
            productos.add(producto);
        }

    }
    @Override
    public String toString() {
        return "Categoria [ID=" + getId() + "] " + nombre + " - " + descripcion;
    }

}
