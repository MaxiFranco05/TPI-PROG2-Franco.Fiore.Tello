# Food Store - Sistema de Gestión de Pedidos
##### Máximo Franco - Octavio Fiore - Elías Tello
##### Comisión 3 - Grupo 10
###
Sistema de consola para la gestión de pedidos de comida. Desarrollado en Java con JDBC y MySQL, siguiendo una arquitectura multicapa con DAO, Servicios y UI por consola.

## Stack Tecnológico

- Java 21
- Maven
- MySQL 8+
- JDBC puro (PreparedStatement, ResultSet)
- dotenv-java (configuración de conexión)

## Requisitos Previos

- JDK 21+ instalado
- MySQL 8+ instalado y corriendo
- Maven 3.8+ instalado
- Variable de entorno `JAVA_HOME` apuntando a JDK 21

## Configuración de Base de Datos

1. Abrir MySQL y ejecutar el script SQL provisto:

```bash
mysql -u root -p < src/main/resources/schema.sql
```

Esto crea la base de datos `pedidos_db`, las tablas y los datos de prueba.

2. Copiar `.env.example` como `.env` y configurar las credenciales:

```bash
cp .env.example .env
```

3. Editar `.env` con tus credenciales de MySQL:

```
DB_URL=jdbc:mysql://localhost:3306/pedidos_db
DB_USER=root
DB_PASSWORD=tu_password
```

> La configuración de persistencia está centralizada en `config/DatabaseConnection.java`, que lee estos valores usando dotenv-java.

## Ejecución

### Con Maven

```bash
mvn clean compile exec:java -Dexec.mainClass="integrado.prog2.Main"
```

## Estructura del Proyecto

```
src/main/java/integrado/prog2/
├── Main.java                     # Punto de entrada
├── config/
│   └── DatabaseConnection.java   # Conexión centralizada a MySQL
├── dao/
│   ├── IDAO.java                 # Interfaz genérica CRUD
│   ├── CategoriaDAO.java
│   ├── DetallePedidoDAO.java
│   ├── PedidoDAO.java
│   ├── ProductoDAO.java
│   └── UsuarioDAO.java
├── entities/
│   ├── Base.java                 # Clase abstracta base
│   ├── Categoria.java
│   ├── DetallePedido.java
│   ├── Pedido.java               # Implementa Calculable
│   ├── Producto.java
│   └── Usuario.java
├── enums/
│   ├── Estado.java               # PENDIENTE, CONFIRMADO, TERMINADO, CANCELADO
│   ├── FormaPago.java            # TARJETA, TRANSFERENCIA, EFECTIVO
│   └── Rol.java                  # ADMIN, USUARIO
├── exception/
│   ├── EntityNotFoundException.java
│   ├── MailDuplicadoException.java
│   ├── PedidoInvalidoException.java
│   ├── PrecioInvalidoException.java
│   ├── ProductoInvalidoException.java
│   ├── StockInvalidoException.java
│   └── UsuarioInvalidoException.java
├── interfaces/
│   └── Calculable.java           # calcularTotal()
├── service/
│   ├── CategoriaService.java
│   ├── PedidoService.java         # Contiene la lógica transaccional
│   ├── ProductoService.java
│   └── UsuarioService.java
└── ui/
    ├── Menu.java                 # Submenús CRUD
    └── MenuPrincipal.java        # Menú principal
```

## Funcionalidades

### CRUD Completo
- **Categorías**: Listar, crear (con validación de nombre único), editar, eliminar (baja lógica con confirmación)
- **Productos**: Listar (con filtro opcional por categoría), crear, editar, eliminar (baja lógica con confirmación)
- **Usuarios**: Listar, crear, editar, eliminar (baja lógica con confirmación)
- **Pedidos**: Listar (con filtro opcional por usuario), crear con múltiples detalles, actualizar estado/forma de pago, cancelar (con confirmación y reintegro de stock)

### Validaciones de Negocio
- Precio de producto no puede ser negativo
- Stock de producto no puede ser negativo
- Nombre de producto no puede estar vacío
- Nombre de categoría único (validación antes de insertar)
- Mail de usuario único (UNIQUE constraint + captura de excepción)
- Pedido requiere un usuario asociado
- Cantidad en detalle de pedido debe ser mayor a 0
- Stock suficiente al agregar producto a un pedido
- Confirmación obligatoria (S/N) antes de cualquier eliminación

### Transacciones
La creación de pedidos con múltiples detalles se maneja con transacciones manuales:
- `setAutoCommit(false)` al inicio
- `commit()` al finalizar correctamente
- `rollback()` si ocurre cualquier excepción

Para forzar un rollback en la demostración: durante la creación de un pedido, desconectar el servidor MySQL o cerrar la conexión antes de finalizar.

### Baja Lógica (Soft Delete)
Ningún registro se elimina físicamente. Todas las bajas usan `UPDATE tabla SET eliminado = true WHERE id = ?` y las listas filtran con `WHERE eliminado = false`.

## Enlaces

- Repositorio: https://github.com/MaxiFranco05/TPI-PROG2-Franco.Fiore.Tello