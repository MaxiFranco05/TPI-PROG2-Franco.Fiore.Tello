package User_I;

import enums.Estado;
import enums.FormaPago;
import enums.Rol;
import entities.Categoria;
import entities.Producto;
import entities.Usuario;
import entities.Pedido;
import entities.DetallePedido;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Menu {

    private final Scanner scanner;

    private final List<Categoria> categorias;
    private final List<Producto> productos;
    private final List<Usuario> usuarios;
    private final List<Pedido> pedidos;

    public Menu() {
        this.scanner = new Scanner(System.in);

        this.categorias = new ArrayList<>();
        this.productos = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    public void iniciar() {
        int opcion = -1;

        do {
            System.out.println("\n=======================================");
            System.out.println("              MENU PRINCIPAL             ");
            System.out.println("=======================================");
            System.out.println("1. Gestión de Categorías");
            System.out.println("2. Gestión de Productos");
            System.out.println("3. Gestión de Usuarios");
            System.out.println("4. Gestión de Pedidos");
            System.out.println("0. Salir del sistema");
            System.out.println("=======================================");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1 -> menuCategorias();
                    case 2 -> menuProductos();
                    case 3 -> menuUsuarios();
                    case 4 -> menuPedidos();
                    case 0 -> System.out.println("\nCerrando Food Store... ¡Hasta luego!");
                    default -> System.out.println("Error: Opción inválida. Ingrese un número entre 0 y 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error de entrada: Por favor, ingrese un número entero válido.");
                opcion = -1;
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);

        scanner.close();
    }

    // CATEGORÍAS

    private void menuCategorias() {
        int opcion = -1;
        do {
            System.out.println("\n--- GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Listar categorías");
            System.out.println("2. Crear categoría");
            System.out.println("3. Editar categoría");
            System.out.println("4. Eliminar categoría (Baja Lógica)");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> {
                        System.out.println("\n>> Listado de Categorías:");
                        boolean hayCategorias = false;
                        for (Categoria cat : categorias) {
                            if (!cat.isEliminado()) {
                                System.out.println(cat);
                                hayCategorias = true;
                            }
                        }
                        if (!hayCategorias) {
                            System.out.println("No hay categorías registradas.");
                        }
                    }
                    case 2 -> {
                        System.out.print("\nNombre de la categoría: ");
                        String nombre = scanner.nextLine();
                        Categoria nuevaCategoria = new Categoria(nombre);
                        categorias.add(nuevaCategoria);
                        System.out.println("Categoría creada exitosamente: " + nuevaCategoria);
                    }
                    case 3 -> {
                        System.out.print("\nID de la categoría a editar: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        Categoria catAEditar = null;
                        for (Categoria cat : categorias) {
                            if (cat.getId().equals(id) && !cat.isEliminado()) {
                                catAEditar = cat;
                                break;
                            }
                        }
                        if (catAEditar != null) {
                            System.out.print("Nuevo nombre: ");
                            String nuevoNombre = scanner.nextLine();
                            catAEditar.setNombre(nuevoNombre);
                            System.out.println("Categoría editada exitosamente.");
                        } else {
                            System.out.println("Error: Categoría no encontrada.");
                        }
                    }
                    case 4 -> {
                        System.out.print("\nID de la categoría a eliminar: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        Categoria catAEliminar = null;
                        for (Categoria cat : categorias) {
                            if (cat.getId().equals(id) && !cat.isEliminado()) {
                                catAEliminar = cat;
                                break;
                            }
                        }
                        if (catAEliminar != null) {
                            catAEliminar.setEliminado(true);
                            System.out.println("Categoría eliminada exitosamente (Baja lógica).");
                        } else {
                            System.out.println("Error: Categoría no encontrada.");
                        }
                    }
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción incorrecta.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un valor numérico.");
            } catch (Exception e) {
                System.out.println("Error de negocio: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    // PRODUCTOS

    private void menuProductos() {
        int opcion = -1;
        do {
            System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Listar productos");
            System.out.println("2. Crear producto");
            System.out.println("3. Editar producto");
            System.out.println("4. Eliminar producto (Baja Lógica)");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> {
                        System.out.println("\n>> Listado de Productos:");
                        boolean hayProductos = false;
                        for (Producto prod : productos) {
                            if (!prod.isEliminado()) {
                                System.out.println(prod);
                                hayProductos = true;
                            }
                        }
                        if (!hayProductos) {
                            System.out.println("No hay productos registrados.");
                        }
                    }
                    case 2 -> {
                        System.out.print("\nNombre del producto: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Precio: ");
                        double precio = Double.parseDouble(scanner.nextLine());
                        System.out.print("Stock: ");
                        int stock = Integer.parseInt(scanner.nextLine());
                        System.out.print("ID Categoría: ");
                        Long idCat = Long.parseLong(scanner.nextLine());

                        // Buscar la categoría
                        Categoria catEncontrada = null;
                        for (Categoria cat : categorias) {
                            if (cat.getId().equals(idCat) && !cat.isEliminado()) {
                                catEncontrada = cat;
                                break;
                            }
                        }

                        if (catEncontrada != null) {
                            Producto nuevo = new Producto(nombre, precio, "Sin descripción", stock, "sin imagen.png", catEncontrada);
                            productos.add(nuevo);
                            System.out.println("Producto creado exitosamente: " + nuevo);
                        } else {
                            System.out.println("Error: Categoría no encontrada.");
                        }
                    }
                    case 3 -> {
                        System.out.print("\nID del producto a editar: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        Producto prodAEditar = null;
                        for (Producto p : productos) {
                            if (p.getId().equals(id) && !p.isEliminado()) {
                                prodAEditar = p;
                                break;
                            }
                        }

                        if (prodAEditar != null) {
                            System.out.print("Nuevo nombre: ");
                            String nuevoNombre = scanner.nextLine();
                            System.out.print("Nuevo precio: ");
                            double nuevoPrecio = Double.parseDouble(scanner.nextLine());
                            System.out.print("Nuevo stock: ");
                            int nuevoStock = Integer.parseInt(scanner.nextLine());

                            prodAEditar.setNombre(nuevoNombre);
                            prodAEditar.setPrecio(nuevoPrecio);
                            prodAEditar.setStock(nuevoStock);
                            System.out.println("Producto editado exitosamente.");
                        } else {
                            System.out.println("Error: Producto no encontrado.");
                        }
                    }
                    case 4 -> {
                        System.out.print("\nID del producto a eliminar: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        Producto prodAEliminar = null;
                        for (Producto p : productos) {
                            if (p.getId().equals(id) && !p.isEliminado()) {
                                prodAEliminar = p;
                                break;
                            }
                        }

                        if (prodAEliminar != null) {
                            prodAEliminar.setEliminado(true);
                            System.out.println("Producto eliminado exitosamente (Baja lógica).");
                        } else {
                            System.out.println("Error: Producto no encontrado.");
                        }
                    }
                    case 0 -> System.out.println("Volviendo...");
                    default -> System.out.println("Opción incorrecta.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Formato numérico incorrecto.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    // USUARIOS

    private void menuUsuarios() {
        int opcion = -1;
        do {
            System.out.println("\n--- GESTIÓN DE USUARIOS ---");
            System.out.println("1. Listar usuarios");
            System.out.println("2. Crear usuario");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> {
                        System.out.println("\n>> Listado de Usuarios:");
                        boolean hayUsuarios = false;
                        for (Usuario user : usuarios) {
                            if (!user.isEliminado()) {
                                System.out.println(user);
                                hayUsuarios = true;
                            }
                        }
                        if (!hayUsuarios) {
                            System.out.println("No hay usuarios registrados.");
                        }
                    }
                    case 2 -> {
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
                        System.out.print("Rol (1. ADMIN, 2. CLIENTE): ");
                        int rolSel = Integer.parseInt(scanner.nextLine());
                        Rol rol = (rolSel == 1) ? Rol.ADMIN : Rol.USUARIO;

                        Usuario nuevo = new Usuario(nombre, apellido, email, celular, contrasena, rol);
                        usuarios.add(nuevo);
                        System.out.println("Usuario creado exitosamente: " + nuevo);
                    }
                    case 0 -> System.out.println("Volviendo...");
                    default -> System.out.println("Opción incorrecta.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Entrada numérica inválida.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }


    // PEDIDOS

    private void menuPedidos() {
        int opcion = -1;
        do {
            System.out.println("\n--- GESTIÓN DE PEDIDOS ---");
            System.out.println("1. Listar pedidos activos");
            System.out.println("2. Crear nuevo pedido (con detalles)");
            System.out.println("3. Actualizar Estado / Forma de Pago");
            System.out.println("4. Cancelar/Eliminar pedido");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> {
                        System.out.println("\n>> Listado de Pedidos:");
                        boolean hayPedidos = false;
                        for (Pedido ped : pedidos) {
                            if (!ped.isEliminado()) {
                                System.out.println(ped);
                                for (DetallePedido det : ped.getDetalles()) {
                                    System.out.println(det);
                                }
                                hayPedidos = true;
                            }
                        }
                        if (!hayPedidos) {
                            System.out.println("No hay pedidos registrados.");
                        }
                    }
                    case 2 -> registrarPedidoConDetalles();
                    case 3 -> {
                        System.out.print("\nID Pedido a actualizar: ");
                        Long idPed = Long.parseLong(scanner.nextLine());
                        Pedido pedAActualizar = null;
                        for (Pedido p : pedidos) {
                            if (p.getId().equals(idPed) && !p.isEliminado()) {
                                pedAActualizar = p;
                                break;
                            }
                        }
                        if (pedAActualizar != null) {
                            System.out.println("1. PENDIENTE, 2. CONFIRMADO, 3. TERMINADO, 4. CANCELADO");
                            System.out.print("Nuevo Estado: ");
                            int est = Integer.parseInt(scanner.nextLine());
                            Estado estado = Estado.values()[est - 1];
                            pedAActualizar.setEstado(estado);

                            // Si se cancela el pedido, devolvemos el stock
                            if (estado == Estado.CANCELADO) {
                                for (DetallePedido d : pedAActualizar.getDetalles()) {
                                    Producto prod = d.getProducto();
                                    prod.setStock(prod.getStock() + d.getCantidad());
                                }
                            }

                            System.out.print("¿Desea cambiar la Forma de Pago? (S/N): ");
                            String cambiarFP = scanner.nextLine();
                            if (cambiarFP.equalsIgnoreCase("S")) {
                                System.out.println("1. TARJETA, 2. TRANSFERENCIA, 3. EFECTIVO");
                                System.out.print("Seleccione Forma de Pago: ");
                                int fpSel = Integer.parseInt(scanner.nextLine());
                                FormaPago formaPago = FormaPago.values()[fpSel - 1];
                                pedAActualizar.setFormaPago(formaPago);
                            }
                            System.out.println("Pedido actualizado con éxito.");
                        } else {
                            System.out.println("Error: Pedido no encontrado.");
                        }
                    }
                    case 4 -> {
                        System.out.print("\nID Pedido a eliminar: ");
                        Long idPed = Long.parseLong(scanner.nextLine());
                        Pedido pedAEliminar = null;
                        for (Pedido p : pedidos) {
                            if (p.getId().equals(idPed) && !p.isEliminado()) {
                                pedAEliminar = p;
                                break;
                            }
                        }
                        if (pedAEliminar != null) {
                            // Devolver stock
                            for (DetallePedido d : pedAEliminar.getDetalles()) {
                                Producto prod = d.getProducto();
                                prod.setStock(prod.getStock() + d.getCantidad());
                            }
                            pedAEliminar.setEliminado(true);
                            pedAEliminar.setEstado(Estado.CANCELADO);
                            System.out.println("Pedido eliminado y cancelado con éxito. Se reintegró el stock.");
                        } else {
                            System.out.println("Error: Pedido no encontrado.");
                        }
                    }
                    case 0 -> System.out.println("Volviendo...");
                    default -> System.out.println("Opción incorrecta.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error Datos numéricos mal ingresados.");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error Selección de Enum fuera de rango.");
            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void registrarPedidoConDetalles() {
        try {
            System.out.println("\n--- NUEVO PEDIDO ---");
            System.out.print("ID del Usuario cliente: ");
            Long idUsuario = Long.parseLong(scanner.nextLine());

            Usuario usuarioEncontrado = null;
            for (Usuario u : usuarios) {
                if (u.getId().equals(idUsuario) && !u.isEliminado()) {
                    usuarioEncontrado = u;
                    break;
                }
            }

            if (usuarioEncontrado == null) {
                System.out.println("Error: Usuario no encontrado o eliminado.");
                return;
            }

            System.out.println("Formas de Pago: 1. TARJETA, 2. TRANSFERENCIA, 3. EFECTIVO");
            System.out.print("Seleccione Forma de Pago: ");
            int fpSel = Integer.parseInt(scanner.nextLine());
            FormaPago formaPago = FormaPago.values()[fpSel - 1];

            Pedido nuevoPedido = new Pedido(usuarioEncontrado, formaPago);

            boolean seguir = true;
            while (seguir) {
                System.out.print("\nID del Producto a agregar: ");
                Long idProducto = Long.parseLong(scanner.nextLine());

                Producto prodEncontrado = null;
                for (Producto p : productos) {
                    if (p.getId().equals(idProducto) && !p.isEliminado()) {
                        prodEncontrado = p;
                        break;
                    }
                }

                if (prodEncontrado == null) {
                    System.out.println("Error: Producto no encontrado o eliminado.");
                    System.out.print("¿Intentar de nuevo? (S/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("N")) {
                        seguir = false;
                    }
                    continue;
                }

                System.out.print("Cantidad: ");
                int cantidad = Integer.parseInt(scanner.nextLine());

                if (prodEncontrado.getStock() < cantidad) {
                    System.out.println("Error: Stock insuficiente. Stock actual: " + prodEncontrado.getStock());
                    continue;
                }

                // Agregar detalle
                nuevoPedido.addDetallePedido(cantidad, prodEncontrado.getPrecio(), prodEncontrado);
                // Restar stock
                prodEncontrado.setStock(prodEncontrado.getStock() - cantidad);

                System.out.println("Producto agregado al carrito.");

                System.out.print("¿Agregar otro producto? (S/N): ");
                String rta = scanner.nextLine();
                if (rta.equalsIgnoreCase("N")) {
                    seguir = false;
                }
            }

            if (!nuevoPedido.getDetalles().isEmpty()) {
                pedidos.add(nuevoPedido);
                usuarioEncontrado.addPedido(nuevoPedido);
                System.out.println("Pedido registrado y total calculado con éxito.");
                System.out.println("Total del Pedido: $" + nuevoPedido.getTotal());
            } else {
                System.out.println("No se agregaron productos. Pedido descartado.");
            }

        } catch (Exception e) {
            System.out.println("No se pudo crear el pedido: " + e.getMessage());
        }
    }
}
