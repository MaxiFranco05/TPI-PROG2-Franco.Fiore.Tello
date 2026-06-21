package integrado.prog2.ui;

import integrado.prog2.service.CategoriaService;
import integrado.prog2.service.ProductoService;
import integrado.prog2.service.UsuarioService;
import integrado.prog2.service.PedidoService;

import java.util.Scanner;

public class MenuPrincipal {

    private final Scanner scanner;
    private final CategoriaService categoriaService = new CategoriaService();
    private final ProductoService productoService = new ProductoService();
    private final UsuarioService usuarioService = new UsuarioService();
    private final PedidoService pedidoService = new PedidoService();

    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = -1;
        do {
            System.out.println("\n=======================================");
            System.out.println("     SISTEMA DE PEDIDOS (FOOD STORE)  ");
            System.out.println("=======================================");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.println("=======================================");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> {
                        Menu menuCat = new Menu(scanner, categoriaService);
                        menuCat.iniciarCategoria();
                    }
                    case 2 -> {
                        Menu menuProd = new Menu(scanner, productoService, categoriaService);
                        menuProd.iniciarProducto();
                    }
                    case 3 -> {
                        Menu menuUser = new Menu(scanner, usuarioService);
                        menuUser.iniciarUsuario();
                    }
                    case 4 -> {
                        Menu menuPed = new Menu(scanner, pedidoService, productoService, usuarioService);
                        menuPed.iniciarPedido();
                    }
                    case 0 -> System.out.println("Saliendo del sistema... ¡Hasta luego!");
                    default -> System.out.println("Error: Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);

        scanner.close();
    }
}