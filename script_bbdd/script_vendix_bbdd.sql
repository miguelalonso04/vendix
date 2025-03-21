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
    direccion_id BIGINT,
    usuario_id BIGINT,
    precioTotal DOUBLE NOT NULL,
    estado ENUM('PENDIENTE', 'ENVIADO', 'ENTREGADO', 'CANCELADO') NOT NULL,
    fechaPedido TIMESTAMP NOT NULL,
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
    comentario TEXT,
    valoracion INT NOT NULL,
    FOREIGN KEY (producto_id) REFERENCES PRODUCTOS(id)
);

CREATE TABLE CESTA_PRODUCTOS (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    total DOUBLE NOT NULL
);

CREATE TABLE CESTA_PRODUCTOS_PRODUCTO (
    cesta_id BIGINT,
    producto_id BIGINT,
    PRIMARY KEY (cesta_id, producto_id),
    FOREIGN KEY (cesta_id) REFERENCES CESTA_PRODUCTOS(id),
    FOREIGN KEY (producto_id) REFERENCES PRODUCTOS(id)
);

INSERT INTO ROLES (ID, TIPO) VALUES (1, 'ADMIN');
INSERT INTO ROLES (ID, TIPO) VALUES (2, 'USER');

#USUARIOS DE PRUEBA 

INSERT INTO USUARIOS (ID, USERNAME, PASSWORD, EMAIL ,FIRST_NAME, LAST_NAME, TELEFONO, ENABLED, lastPasswordResetDate) VALUES
(1, 'u1', '1234','u1@gmail.com', 'Pepín', 'Gálvez Ridruejuela', '+34 636598871', 1, null);

INSERT INTO USUARIOS_ROLES(ID_USER, ID_ROL) VALUES
(1,1);

INSERT INTO CATEGORIAS (nombre, descripcion) 
VALUES ('Electrónica', 'Dispositivos y gadgets electrónicos');
