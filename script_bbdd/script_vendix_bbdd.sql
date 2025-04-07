DROP DATABASE IF EXISTS vendixbbdd;
CREATE DATABASE vendixbbdd CHARACTER SET utf8mb4;
USE vendixbbdd;

CREATE TABLE USUARIOS (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    FIRST_NAME VARCHAR(255),
    LAST_NAME VARCHAR(255),
    telefono VARCHAR(20),
    enabled BOOLEAN,
    lastPasswordResetDate TIMESTAMP
);

CREATE TABLE ROLES (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo 	VARCHAR(50) 		NOT NULL
);

CREATE TABLE USUARIOS_ROLES (
    ID_USER BIGINT,
    ID_ROL BIGINT,
    PRIMARY KEY (ID_USER, ID_ROL),
    FOREIGN KEY (ID_USER) REFERENCES USUARIOS(id),
    FOREIGN KEY (ID_ROL) REFERENCES ROLES(id)
);

CREATE TABLE CATEGORIAS (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT
);

CREATE TABLE PRODUCTOS (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    precio DOUBLE NOT NULL,
    stock INT NOT NULL,
    disponible BOOLEAN NOT NULL,
    categoria_id BIGINT,
    FOREIGN KEY (categoria_id) REFERENCES CATEGORIAS(id)
);

CREATE TABLE DIRECCION (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    ID_USER BIGINT,
    calle VARCHAR(255) NOT NULL,
    ciudad VARCHAR(255) NOT NULL,
    provincia VARCHAR(255) NOT NULL,
    CODIGO_POSTAL VARCHAR(20) NOT NULL,
    pais VARCHAR(255) NOT NULL,
    FOREIGN KEY (ID_USER) REFERENCES USUARIOS(id)
);

CREATE TABLE PEDIDOS (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cesta_id BIGINT,
    direccion_id BIGINT,
    usuario_id BIGINT,
    precioTotal DOUBLE,
    estado VARCHAR(20),
    fechaPedido TIMESTAMP,
    FOREIGN KEY (direccion_id) REFERENCES DIRECCION(id),
    FOREIGN KEY (usuario_id) REFERENCES USUARIOS(id)
);

CREATE TABLE PEDIDO_PRODUCTOS (
    pedido_id BIGINT,
    producto_id BIGINT,
    PRIMARY KEY (pedido_id, producto_id),
    FOREIGN KEY (pedido_id) REFERENCES PEDIDOS(id),
    FOREIGN KEY (producto_id) REFERENCES PRODUCTOS(id)
);

CREATE TABLE VALORACIONES_PRODUCTO (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    producto_id BIGINT,
    usuario_id BIGINT,
    comentario TEXT,
    valoracion INT NOT NULL,
    FOREIGN KEY (producto_id) REFERENCES PRODUCTOS(id),
    FOREIGN KEY (usuario_id) REFERENCES USUARIOS(id)
);

CREATE TABLE CESTA_PRODUCTOS (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    total DOUBLE NOT NULL
);

CREATE TABLE CESTA_PRODUCTOS_PRODUCTO (
    cesta_id BIGINT,
    producto_id BIGINT,
    cantidad INT NOT NULL DEFAULT 1,
    PRIMARY KEY (cesta_id, producto_id),
    FOREIGN KEY (cesta_id) REFERENCES CESTA_PRODUCTOS(id),
    FOREIGN KEY (producto_id) REFERENCES PRODUCTOS(id)
);

INSERT INTO ROLES (ID, TIPO) VALUES (1, 'ADMIN');
INSERT INTO ROLES (ID, TIPO) VALUES (2, 'USER');

INSERT INTO USUARIOS (id,username, password, email, FIRST_NAME, LAST_NAME, telefono, enabled, lastPasswordResetDate)  
VALUES (1,'admin', '$2a$10$27wkx.cJ8xJCGWVCY3dg/.OcnvhPWK9rQhMDDKWdJZBno77tyVrK6', 'admin@gmail.com', 'Miguel', 'Alonso', '123456789', TRUE, NOW());

INSERT INTO USUARIOS_ROLES(ID_USER,ID_ROL) VALUES (1,1);

INSERT INTO CATEGORIAS (nombre, descripcion) VALUES
('Libros', 'Categoría dedicada a libros de todo tipo, incluyendo novelas, educación y más.'),
('Electrónica', 'Dispositivos y artículos electrónicos como teléfonos, computadoras, videjuegos y accesorios.'),
('Moda', 'Ropa, calzado y accesorios de moda para todas las edades.'),
('Deportes', 'Equipamiento y artículos relacionados con el deporte y la actividad física.'),
('Automocion', 'Productos y accesorios para automóviles, motos y otros vehículos.'),
('Otros', 'Otro tipo de productos.');

