package User_I;

import enums.Rol;
import entities.Categoria;
import entities.Producto;
import entities.Usuario;
import entities.Pedido;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class MenuPrincipal {

    private final Scanner scanner;
    private final List<Categoria> categorias;
    private final List<Producto> productos;
    private final List<Usuario> usuarios;
    private final List<Pedido> pedidos;

    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
        this.categorias = new ArrayList<>();
        this.productos = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        precargarDatos();
    }

    private void precargarDatos() {
        // 1. Precargar Usuarios (Admin y Cliente)
        Usuario admin = new Usuario("Admin", "General", "admin@mail.com", "123", "admin123", Rol.ADMIN);
        Usuario cliente = new Usuario("Juan", "Perez", "cliente@mail.com", "222", "cliente123", Rol.USUARIO);
        usuarios.add(admin);
        usuarios.add(cliente);

        // 2. Precargar Categorías
        Categoria catBebidas = new Categoria("Bebidas", "Refrescos y aguas");
        Categoria catComidas = new Categoria("Comidas", "Pizzas y hamburguesas");
        categorias.add(catBebidas);
        categorias.add(catComidas);

        // 3. Precargar Productos
        Producto prod1 = new Producto("Coca Cola", 1500.0, "Gaseosa de 500ml", 15, "coca.png", catBebidas);
        Producto prod2 = new Producto("Hamburguesa Completa", 4500.0, "Carne, queso y lechuga", 8, "burger.png", catComidas);
        Producto prod3 = new Producto("Agua Mineral", 1000.0, "Agua sin gas 500ml", 25, "agua.png", catBebidas);
        productos.add(prod1);
        productos.add(prod2);
        productos.add(prod3);
    }

    public void iniciar() {
        int opcion = -1;

        do {
            System.out.println("\n=======================================");
            System.out.println("          BIENVENIDO A FOOD STORE        ");
            System.out.println("=======================================");
            System.out.println("1. Ingresar como Administrador");
            System.out.println("2. Ingresar como Cliente");
            System.out.println("3. Registrarse como Cliente Nuevo");
            System.out.println("0. Salir del sistema");
            System.out.println("=======================================");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> iniciarSesionAdmin();
                    case 2 -> {
                        MenuCliente menuCliente = new MenuCliente(scanner, categorias, productos, usuarios, pedidos);
                        menuCliente.iniciar();
                    }
                    case 3 -> registrarClienteNuevo();
                    case 0 -> System.out.println("\nCerrando Food Store... ¡Hasta luego!");
                    default -> System.out.println("Error: Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número entero válido.");
                opcion = -1;
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);

        scanner.close();
    }

    private void iniciarSesionAdmin() {
        System.out.println("\n--- INICIO DE SESIÓN ADMINISTRADOR ---");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        Usuario adminLogueado = null;
        for (Usuario u : usuarios) {
            if (u.getMail().equalsIgnoreCase(email) && u.getContrasena().equals(contrasena) && u.getRol() == Rol.ADMIN && !u.isEliminado()) {
                adminLogueado = u;
                break;
            }
        }

        if (adminLogueado != null) {
            System.out.println("\n¡Acceso concedido! Bienvenido, Administrador " + adminLogueado.getNombre() + ".");
            Menu menuAdmin = new Menu(scanner, categorias, productos, usuarios, pedidos);
            menuAdmin.iniciar();
        } else {
            System.out.println("Error: Credenciales de administrador incorrectas o sin privilegios.");
        }
    }

    private void registrarClienteNuevo() {
        try {
            System.out.println("\n--- REGISTRARSE COMO CLIENTE ---");
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();

            // Validar que el email no esté repetido
            for (Usuario u : usuarios) {
                if (u.getMail().equalsIgnoreCase(email)) {
                    System.out.println("Error: El correo ya está registrado.");
                    return;
                }
            }

            System.out.print("Celular: ");
            String celular = scanner.nextLine();
            System.out.print("Contraseña: ");
            String contrasena = scanner.nextLine();

            Usuario nuevo = new Usuario(nombre, apellido, email, celular, contrasena, Rol.USUARIO);
            usuarios.add(nuevo);
            System.out.println("\nRegistro completado con éxito. Ahora puede ingresar como cliente.");
        } catch (Exception e) {
            System.out.println("Error al registrarse: " + e.getMessage());
        }
    }
}
