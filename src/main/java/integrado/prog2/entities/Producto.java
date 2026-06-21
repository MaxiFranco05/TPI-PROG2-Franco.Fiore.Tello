package integrado.prog2.entities;

import integrado.prog2.exception.PrecioInvalidoException;
import integrado.prog2.exception.StockInvalidoException;
import java.util.Objects;

public class Producto extends Base{
    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private Boolean disponible;
    private Categoria categoria;

    public Producto(String nombre, Double precio, String descripcion, int stock, String imagen, Categoria categoria) {
        this.setNombre(nombre);
        this.setPrecio(precio);
        this.setDescripcion(descripcion);
        this.setStock(stock);
        this.setImagen(imagen);
        this.disponible = stock>0;
        this.setCategoria(categoria);
    }

    public String getNombre() { return nombre; }
    public Double getPrecio() { return precio; }
    public String getDescripcion() { return descripcion; }
    public int getStock() { return stock; }
    public String getImagen() { return imagen; }
    public Boolean getDisponible() { return disponible; }
    public Categoria getCategoria() { return categoria; }

    public void setNombre(String nombre){
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
        else {
            this.descripcion = "No tiene descripcion";
        }
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
        if(categoria!=null && !categoria.getProductos().contains(this)){
            categoria.addProductoToProductos(this);
        }
    }
    public void setImagen(String imagen) {
        if (imagen != null && !imagen.isBlank()) {
            this.imagen = imagen;
        } else {
            this.imagen = "sin imagen.png";
        }
    }
    public void setPrecio(Double precio) {
        if (precio != null && precio >= 0) {
            this.precio = precio;
        } else {
            throw new PrecioInvalidoException("El precio no puede ser negativo ni nulo.");
        }
    }

    public void setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
            this.disponible = stock > 0;
        } else {
            throw new StockInvalidoException("El stock no puede ser negativo.");
        }
    }

    @Override
    public String toString() {
        return "Producto [ID=" + getId() + "] " + this.getNombre() +
                " | Precio: $" + this.getPrecio() +
                " | Stock: " + this.getStock() +
                " | Categoría: " + (this.getCategoria() != null ? this.getCategoria().getNombre() : "Sin categoría") +
                " | Disponible: " + (this.getDisponible() ? "Sí" : "No");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Producto otro)) return false;
        return Objects.equals(this.getId(), otro.getId());
    }
}