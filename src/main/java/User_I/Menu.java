package User_I;

import enums.Estado;
import enums.FormaPago;
import entities.Categoria;
import entities.Producto;
import entities.Usuario;
import entities.Pedido;
import entities.DetallePedido;
import service.*;

import java.util.Scanner;
import java.util.List;

public class Menu {

    private final Scanner scanner;
    private CategoriaService categoriaService;
    private ProductoService productoService;
    private UsuarioService usuarioService;
    private PedidoService pedidoService;

    public Menu(Scanner scanner, CategoriaService categoriaService) {
        this.scanner = scanner;
        this.categoriaService = categoriaService;
    }

    public Menu(Scanner scanner, ProductoService productoService, CategoriaService categoriaService) {
        this.scanner = scanner;
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    public Menu(Scanner scanner, UsuarioService usuarioService) {
        this.scanner = scanner;
        this.usuarioService = usuarioService;
    }

    public Menu(Scanner scanner, PedidoService pedidoService, ProductoService productoService, UsuarioService usuarioService) {
        this.scanner = scanner;
        this.pedidoService = pedidoService;
        this.productoService = productoService;
        this.usuarioService = usuarioService;
    }

    // ======== CATEGORÍAS ========

    public void iniciarCategoria() {
        int opcion = -1;
        do {
            System.out.println("\n--- GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> listarCategorias();
                    case 2 -> crearCategoria();
                    case 3 -> editarCategoria();
                    case 4 -> eliminarCategoria();
                    case 0 -> System.out.println("Volviendo...");
                    default -> System.out.println("Opción incorrecta.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void listarCategorias() {
        System.out.println("\n>> Listado de Categorías:");
        List<Categoria> lista = categoriaService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay categorías registradas.");
        } else {
            for (Categoria c : lista) {
                System.out.println(c);
            }
        }
    }

    private void crearCategoria() {
        System.out.print("\nNombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        Categoria nueva = new Categoria(nombre, descripcion);
        categoriaService.guardar(nueva);
        System.out.println("Categoría creada: " + nueva);
    }

    private void editarCategoria() {
        listarCategorias();
        System.out.print("\nID de la categoría a editar: ");
        Long id = Long.parseLong(scanner.nextLine());
        Categoria c = categoriaService.buscarPorId(id);
        if (c == null || c.isEliminado()) {
            System.out.println("Error: Categoría no encontrada.");
            return;
        }
        System.out.print("Nuevo nombre (" + c.getNombre() + "): ");
        String nombre = scanner.nextLine();
        if (!nombre.isBlank()) c.setNombre(nombre);
        System.out.print("Nueva descripción (" + c.getDescripcion() + "): ");
        String desc = scanner.nextLine();
        if (!desc.isBlank()) c.setDescripcion(desc);
        categoriaService.actualizar(c);
        System.out.println("Categoría editada exitosamente.");
    }

    private void eliminarCategoria() {
        listarCategorias();
        System.out.print("\nID de la categoría a eliminar: ");
        Long id = Long.parseLong(scanner.nextLine());
        Categoria c = categoriaService.buscarPorId(id);
        if (c == null || c.isEliminado()) {
            System.out.println("Error: Categoría no encontrada.");
            return;
        }
        categoriaService.eliminar(id);
        System.out.println("Categoría eliminada (baja lógica).");
    }

    // ======== PRODUCTOS ========

    public void iniciarProducto() {
        int opcion = -1;
        do {
            System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> listarProductos();
                    case 2 -> crearProducto();
                    case 3 -> editarProducto();
                    case 4 -> eliminarProducto();
                    case 0 -> System.out.println("Volviendo...");
                    default -> System.out.println("Opción incorrecta.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void listarProductos() {
        System.out.println("\n>> Listado de Productos:");
        List<Producto> lista = productoService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            for (Producto p : lista) {
                System.out.println(p);
            }
        }
    }

    private void crearProducto() {
        System.out.print("\nNombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Precio: ");
        double precio = Double.parseDouble(scanner.nextLine());
        System.out.print("Stock: ");
        int stock = Integer.parseInt(scanner.nextLine());
        System.out.print("Descripción: ");
        String desc = scanner.nextLine();

        listarCategorias();
        System.out.print("ID Categoría: ");
        Long idCat = Long.parseLong(scanner.nextLine());
        Categoria cat = categoriaService.buscarPorId(idCat);
        if (cat == null || cat.isEliminado()) {
            System.out.println("Error: Categoría no encontrada.");
            return;
        }

        Producto nuevo = new Producto(nombre, precio, desc, stock, "sin imagen.png", cat);
        productoService.guardar(nuevo);
        System.out.println("Producto creado: " + nuevo);
    }

    private void editarProducto() {
        listarProductos();
        System.out.print("\nID del producto a editar: ");
        Long id = Long.parseLong(scanner.nextLine());
        Producto p = productoService.buscarPorId(id);
        if (p == null || p.isEliminado()) {
            System.out.println("Error: Producto no encontrado.");
            return;
        }
        System.out.print("Nuevo nombre (" + p.getNombre() + "): ");
        String nombre = scanner.nextLine();
        if (!nombre.isBlank()) p.setNombre(nombre);
        System.out.print("Nuevo precio (" + p.getPrecio() + "): ");
        String precioStr = scanner.nextLine();
        if (!precioStr.isBlank()) p.setPrecio(Double.parseDouble(precioStr));
        System.out.print("Nuevo stock (" + p.getStock() + "): ");
        String stockStr = scanner.nextLine();
        if (!stockStr.isBlank()) p.setStock(Integer.parseInt(stockStr));

        productoService.actualizar(p);
        System.out.println("Producto editado exitosamente.");
    }

    private void eliminarProducto() {
        listarProductos();
        System.out.print("\nID del producto a eliminar: ");
        Long id = Long.parseLong(scanner.nextLine());
        Producto p = productoService.buscarPorId(id);
        if (p == null || p.isEliminado()) {
            System.out.println("Error: Producto no encontrado.");
            return;
        }
        productoService.eliminar(id);
        System.out.println("Producto eliminado (baja lógica).");
    }

    // ======== USUARIOS ========

    public void iniciarUsuario() {
        int opcion = -1;
        do {
            System.out.println("\n--- GESTIÓN DE USUARIOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> listarUsuarios();
                    case 2 -> crearUsuario();
                    case 0 -> System.out.println("Volviendo...");
                    default -> System.out.println("Opción incorrecta.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void listarUsuarios() {
        System.out.println("\n>> Listado de Usuarios:");
        List<Usuario> lista = usuarioService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            for (Usuario u : lista) {
                System.out.println(u);
            }
        }
    }

    private void crearUsuario() {
        System.out.print("\nNombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Celular: ");
        String celular = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();
        System.out.print("Rol (1. ADMIN, 2. USUARIO): ");
        int rolSel = Integer.parseInt(scanner.nextLine());
        enums.Rol rol = (rolSel == 1) ? enums.Rol.ADMIN : enums.Rol.USUARIO;

        Usuario nuevo = new Usuario(nombre, apellido, email, celular, contrasena, rol);
        usuarioService.guardar(nuevo);
        System.out.println("Usuario creado: " + nuevo);
    }

    // ======== PEDIDOS ========

    public void iniciarPedido() {
        int opcion = -1;
        do {
            System.out.println("\n--- GESTIÓN DE PEDIDOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear (con detalles)");
            System.out.println("3. Actualizar Estado / Forma de Pago");
            System.out.println("4. Cancelar/Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> listarPedidos();
                    case 2 -> crearPedido();
                    case 3 -> actualizarPedido();
                    case 4 -> eliminarPedido();
                    case 0 -> System.out.println("Volviendo...");
                    default -> System.out.println("Opción incorrecta.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void listarPedidos() {
        System.out.println("\n>> Listado de Pedidos:");
        List<Pedido> lista = pedidoService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
        } else {
            for (Pedido p : lista) {
                System.out.println(p);
                for (DetallePedido d : p.getDetalles()) {
                    System.out.println("  " + d);
                }
            }
        }
    }

    private void crearPedido() {
        try {
            System.out.println("\n--- NUEVO PEDIDO ---");
            listarUsuarios();
            System.out.print("ID del Usuario: ");
            Long idUsuario = Long.parseLong(scanner.nextLine());
            Usuario usuario = usuarioService.buscarPorId(idUsuario);
            if (usuario == null || usuario.isEliminado()) {
                System.out.println("Error: Usuario no encontrado.");
                return;
            }

            System.out.println("Formas de Pago: 1. TARJETA, 2. TRANSFERENCIA, 3. EFECTIVO");
            System.out.print("Seleccione: ");
            int fpSel = Integer.parseInt(scanner.nextLine());
            FormaPago formaPago = FormaPago.values()[fpSel - 1];

            Pedido nuevoPedido = new Pedido(usuario, formaPago);

            boolean seguir = true;
            while (seguir) {
                listarProductos();
                System.out.print("\nID del Producto a agregar: ");
                Long idProducto = Long.parseLong(scanner.nextLine());
                Producto prod = productoService.buscarPorId(idProducto);
                if (prod == null || prod.isEliminado() || !prod.getDisponible()) {
                    System.out.println("Error: Producto no disponible.");
                    System.out.print("¿Intentar de nuevo? (S/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("N")) seguir = false;
                    continue;
                }

                System.out.print("Cantidad: ");
                int cantidad = Integer.parseInt(scanner.nextLine());
                if (cantidad <= 0) {
                    System.out.println("Error: Cantidad debe ser mayor a 0.");
                    continue;
                }
                if (prod.getStock() < cantidad) {
                    System.out.println("Error: Stock insuficiente. Stock actual: " + prod.getStock());
                    continue;
                }

                nuevoPedido.addDetallePedido(cantidad, prod.getPrecio(), prod);
                prod.setStock(prod.getStock() - cantidad);
                productoService.actualizar(prod);
                System.out.println("Producto agregado.");

                System.out.print("¿Agregar otro producto? (S/N): ");
                if (scanner.nextLine().equalsIgnoreCase("N")) seguir = false;
            }

            if (!nuevoPedido.getDetalles().isEmpty()) {
                pedidoService.guardarConDetalles(nuevoPedido);
                System.out.println("Pedido registrado. Total: $" + nuevoPedido.getTotal());
            } else {
                System.out.println("Pedido descartado (sin productos).");
            }
        } catch (Exception e) {
            System.out.println("Error al crear pedido: " + e.getMessage());
        }
    }

    private void actualizarPedido() {
        listarPedidos();
        System.out.print("\nID Pedido a actualizar: ");
        Long id = Long.parseLong(scanner.nextLine());
        Pedido p = pedidoService.buscarPorId(id);
        if (p == null || p.isEliminado()) {
            System.out.println("Error: Pedido no encontrado.");
            return;
        }

        System.out.println("1. PENDIENTE, 2. CONFIRMADO, 3. TERMINADO, 4. CANCELADO");
        System.out.print("Nuevo Estado: ");
        int est = Integer.parseInt(scanner.nextLine());
        Estado estado = Estado.values()[est - 1];
        p.setEstado(estado);

        if (estado == Estado.CANCELADO) {
            for (DetallePedido d : p.getDetalles()) {
                Producto prod = d.getProducto();
                prod.setStock(prod.getStock() + d.getCantidad());
                productoService.actualizar(prod);
            }
        }

        System.out.print("¿Cambiar Forma de Pago? (S/N): ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            System.out.println("1. TARJETA, 2. TRANSFERENCIA, 3. EFECTIVO");
            System.out.print("Seleccione: ");
            int fp = Integer.parseInt(scanner.nextLine());
            p.setFormaPago(FormaPago.values()[fp - 1]);
        }

        pedidoService.actualizar(p);
        System.out.println("Pedido actualizado.");
    }

    private void eliminarPedido() {
        listarPedidos();
        System.out.print("\nID Pedido a eliminar: ");
        Long id = Long.parseLong(scanner.nextLine());
        Pedido p = pedidoService.buscarPorId(id);
        if (p == null || p.isEliminado()) {
            System.out.println("Error: Pedido no encontrado.");
            return;
        }

        for (DetallePedido d : p.getDetalles()) {
            Producto prod = d.getProducto();
            prod.setStock(prod.getStock() + d.getCantidad());
            productoService.actualizar(prod);
        }

        pedidoService.eliminar(id);
        System.out.println("Pedido cancelado y stock reintegrado.");
    }
}