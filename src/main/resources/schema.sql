CREATE DATABASE IF NOT EXISTS pedidos_db;
USE pedidos_db;

CREATE TABLE categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255) DEFAULT '',
    eliminado BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DOUBLE NOT NULL,
    descripcion VARCHAR(255) DEFAULT '',
    stock INT DEFAULT 0,
    imagen VARCHAR(255) DEFAULT 'sin imagen.png',
    disponible BOOLEAN DEFAULT TRUE,
    id_categoria BIGINT,
    eliminado BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_categoria) REFERENCES categoria(id)
);

CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    mail VARCHAR(150) NOT NULL UNIQUE,
    celular VARCHAR(50),
    contrasena VARCHAR(100) NOT NULL,
    rol VARCHAR(20) NOT NULL DEFAULT 'USUARIO',
    eliminado BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    total DOUBLE DEFAULT 0.0,
    forma_pago VARCHAR(20) NOT NULL,
    id_usuario BIGINT NOT NULL,
    eliminado BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id)
);

CREATE TABLE detalle_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT NOT NULL,
    subtotal DOUBLE NOT NULL,
    id_producto BIGINT NOT NULL,
    id_pedido BIGINT NOT NULL,
    eliminado BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_producto) REFERENCES producto(id),
    FOREIGN KEY (id_pedido) REFERENCES pedido(id)
);

-- Datos de ejemplo
INSERT INTO categoria (nombre, descripcion) VALUES ('Bebidas', 'Refrescos y aguas');
INSERT INTO categoria (nombre, descripcion) VALUES ('Comidas', 'Pizzas y hamburguesas');

INSERT INTO usuario (nombre, apellido, mail, celular, contrasena, rol) VALUES ('Admin', 'General', 'admin@mail.com', '123', 'admin123', 'ADMIN');
INSERT INTO usuario (nombre, apellido, mail, celular, contrasena, rol) VALUES ('Juan', 'Perez', 'cliente@mail.com', '222', 'cliente123', 'USUARIO');

INSERT INTO producto (nombre, precio, descripcion, stock, imagen, disponible, id_categoria) VALUES ('Coca Cola', 1500.0, 'Gaseosa de 500ml', 15, 'coca.png', TRUE, 1);
INSERT INTO producto (nombre, precio, descripcion, stock, imagen, disponible, id_categoria) VALUES ('Hamburguesa Completa', 4500.0, 'Carne, queso y lechuga', 8, 'burger.png', TRUE, 2);
INSERT INTO producto (nombre, precio, descripcion, stock, imagen, disponible, id_categoria) VALUES ('Agua Mineral', 1000.0, 'Agua sin gas 500ml', 25, 'agua.png', TRUE, 1);
