package entities;

public class Producto extends Base{
    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private Boolean disponible;
    private Categoria categoria;

    public Producto(String nombre, Double precio, String descripcion, int stock, String imagen,Categoria categoria) {
        this.setNombre(nombre);
        this.setPrecio(precio);
        this.setDescripcion(descripcion);
        this.setStock(stock);
        this.setImagen(imagen);
        this.disponible = stock>0;
        this.setCategoria(categoria);
    }

    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getStock() {
        return stock;
    }

    public String getImagen() {
        return imagen;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setNombre(String nombre){
        if(nombre!=null && !nombre.isBlank()){
            this.nombre =nombre;
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
            // Lancar un Exception para precio
        }
    }

    public void setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
            this.disponible = stock > 0;
        } else {
            // / Lancar un Exception para setProducto
        }
    }

    // Falta agregar toString y equals

}
