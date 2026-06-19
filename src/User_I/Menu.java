package User_I;

import enums.Estado;
import enums.FormaPago;
import enums.Rol;
import entities.Categoria;
import entities.Producto;
import entities.Usuario;
import entities.Pedido;

import java.util.Scanner;

public class Menu {

    private final Scanner scanner;

    private final Categoria categoria;
    private final Producto producto;
    private final Usuario usuario;
    private final Pedido pedido;

    public Menu() {
        this.scanner = new Scanner(System.in);

        this.categoria = new Categoria();
        this.producto = new Producto();
        this.usuario = new Usuario();
        this.pedido = new Pedido();
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
                    }
                    case 2 -> {
                        System.out.print("\nNombre de la categoría: ");
                        String nombre = scanner.nextLine();
                        System.out.println("Categoría creada exitosamente.");
                    }
                    case 3 -> {
                        System.out.print("\nID de la categoría a editar: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = scanner.nextLine();
                    }
                    case 4 -> {
                        System.out.print("\nID de la categoría a eliminar: ");
                        int id = Integer.parseInt(scanner.nextLine());
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
                    }
                    case 2 -> {
                        System.out.print("\nNombre del producto: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Precio: ");
                        double precio = Double.parseDouble(scanner.nextLine());
                        System.out.print("Stock: ");
                        int stock = Integer.parseInt(scanner.nextLine());
                        System.out.print("ID Categoría: ");
                        int idCat = Integer.parseInt(scanner.nextLine());
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
                    }
                    case 2 -> {
                        System.out.print("\nNombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Rol (1. ADMIN, 2. CLIENTE): ");
                        int rolSel = Integer.parseInt(scanner.nextLine());
                        Rol rol = (rolSel == 1) ? Rol.ADMIN : Rol.USUARIO;
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
                    }
                    case 2 -> registrarPedidoConDetalles();
                    case 3 -> {
                        System.out.print("\nID Pedido a actualizar: ");
                        int idPed = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nuevo Estado (1. PENDIENTE, 2. PREPARANDO, 3. ENTREGADO): ");
                        int est = Integer.parseInt(scanner.nextLine());
                        Estado estado = Estado.values()[est - 1];
                    }
                    case 4 -> {
                        System.out.print("\nID Pedido a eliminar: ");
                        int idPed = Integer.parseInt(scanner.nextLine());
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
            int idUsuario = Integer.parseInt(scanner.nextLine());

            System.out.print("Forma de Pago (1. EFECTIVO, 2. TARJETA, 3. MERCADO_PAGO): ");
            int fpSel = Integer.parseInt(scanner.nextLine());
            FormaPago formaPago = FormaPago.values()[fpSel - 1];


            boolean seguir = true;
            while (seguir) {
                System.out.print("\nID del Producto a agregar: ");
                int idProducto = Integer.parseInt(scanner.nextLine());
                System.out.print("Cantidad: ");
                int cantidad = Integer.parseInt(scanner.nextLine());

                System.out.println("Producto agregado al carrito.");

                System.out.print("¿Agregar otro producto? (S/N): ");
                String rta = scanner.nextLine();
                if (rta.equalsIgnoreCase("N")) {
                    seguir = false;
                }
            }

            System.out.println("Pedido registrado y total calculado con éxito");

        } catch (Exception e) {
            System.out.println("No se pudo crear el pedido: " + e.getMessage());
        }
    }
}