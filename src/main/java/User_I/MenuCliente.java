package User_I;

import entities.Categoria;
import entities.Producto;
import entities.Usuario;
import entities.Pedido;

import java.util.Scanner;
import java.util.List;

public class MenuCliente {

    private final Scanner scanner;
    private final List<Categoria> categorias;
    private final List<Producto> productos;
    private final List<Usuario> usuarios;
    private final List<Pedido> pedidos;

    public MenuCliente(Scanner scanner, List<Categoria> categorias, List<Producto> productos, List<Usuario> usuarios, List<Pedido> pedidos) {
        this.scanner = scanner;
        this.categorias = categorias;
        this.productos = productos;
        this.usuarios = usuarios;
        this.pedidos = pedidos;
    }

    public void iniciar() {
        int opcion = -1;
        do {
            System.out.println("\n=======================================");
            System.out.println("            PANEL DE CLIENTE           ");
            System.out.println("=======================================");
            System.out.println("1. Ver catálogo de productos");
            System.out.println("2. Filtrar productos por Categoría");
            System.out.println("3. Buscar producto por Nombre");
            System.out.println("4. Filtrar productos por Rango de Precios");
            System.out.println("0. Volver al Menú Principal");
            System.out.println("=======================================");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> listarCatalogoProductos();
                    case 2 -> filtrarProductosPorCategoria();
                    case 3 -> buscarProductoPorNombre();
                    case 4 -> filtrarProductosPorPrecio();
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Error: Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un número entero válido.");
                opcion = -1;
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void listarCatalogoProductos() {
        System.out.println("\n>> Catálogo de Productos Disponibles:");
        boolean hayProductos = false;
        for (Producto prod : productos) {
            if (!prod.isEliminado() && prod.getDisponible()) {
                System.out.println(prod.getNombre() + " | Precio: $" + prod.getPrecio() + " | Stock: " + prod.getStock() + " | Categoría: " + prod.getCategoria().getNombre());
                hayProductos = true;
            }
        }
        if (!hayProductos) {
            System.out.println("No hay productos disponibles en el catálogo en este momento.");
        }
    }

    private void filtrarProductosPorCategoria() {
        System.out.println("\nCategorías disponibles:");
        for (Categoria cat : categorias) {
            if (!cat.isEliminado()) {
                System.out.println("ID: " + cat.getId() + " - " + cat.getNombre());
            }
        }
        System.out.print("\nIngrese el ID de la Categoría por la cual filtrar: ");
        try {
            Long idCat = Long.parseLong(scanner.nextLine());
            boolean encontrado = false;
            System.out.println("\n>> Productos en la categoría seleccionada:");
            for (Producto prod : productos) {
                if (!prod.isEliminado() && prod.getCategoria().getId().equals(idCat) && prod.getDisponible()) {
                    System.out.println(prod.getNombre() + " | Precio: $" + prod.getPrecio() + " | Stock: " + prod.getStock());
                    encontrado = true;
                }
            }
            if (!encontrado) {
                System.out.println("No se encontraron productos disponibles en esta categoría.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un ID numérico válido.");
        }
    }

    private void buscarProductoPorNombre() {
        System.out.print("\nIngrese el nombre del producto a buscar: ");
        String busqueda = scanner.nextLine().toLowerCase().trim();
        boolean encontrado = false;
        System.out.println("\n>> Resultados de la búsqueda:");
        for (Producto prod : productos) {
            if (!prod.isEliminado() && prod.getNombre().toLowerCase().contains(busqueda) && prod.getDisponible()) {
                System.out.println(prod.getNombre() + " | Precio: $" + prod.getPrecio() + " | Stock: " + prod.getStock() + " | Categoría: " + prod.getCategoria().getNombre());
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("No se encontraron productos que coincidan con la búsqueda.");
        }
    }

    private void filtrarProductosPorPrecio() {
        try {
            System.out.print("\nPrecio mínimo: ");
            double min = Double.parseDouble(scanner.nextLine());
            System.out.print("Precio máximo: ");
            double max = Double.parseDouble(scanner.nextLine());

            if (min > max) {
                System.out.println("Error: El precio mínimo no puede ser mayor al precio máximo.");
                return;
            }

            boolean encontrado = false;
            System.out.println("\n>> Productos en el rango de precio $" + min + " - $" + max + ":");
            for (Producto prod : productos) {
                if (!prod.isEliminado() && prod.getPrecio() >= min && prod.getPrecio() <= max && prod.getDisponible()) {
                    System.out.println(prod.getNombre() + " | Precio: $" + prod.getPrecio() + " | Stock: " + prod.getStock() + " | Categoría: " + prod.getCategoria().getNombre());
                    encontrado = true;
                }
            }
            if (!encontrado) {
                System.out.println("No se encontraron productos en ese rango de precios.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese valores numéricos válidos para los precios.");
        }
    }
}
