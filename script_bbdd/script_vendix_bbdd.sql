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
    stock INT NOT NULL DEFAULT 0,
    disponible BOOLEAN NOT NULL DEFAULT TRUE,
    categoria_id BIGINT,
    usuario_id BIGINT,
    ruta_imagen VARCHAR(255),
    FOREIGN KEY (categoria_id) REFERENCES CATEGORIAS(id),
    FOREIGN KEY (usuario_id) REFERENCES USUARIOS(id)
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

CREATE TABLE pedido_productos (
    pedido_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    PRIMARY KEY (pedido_id, producto_id),
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
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
VALUES (1,'admin', '$2a$10$27wkx.cJ8xJCGWVCY3dg/.OcnvhPWK9rQhMDDKWdJZBno77tyVrK6', 'admin@gmail.com', 'Miguel', 'Alonso', '+34 634 12 31 23', TRUE, NOW()),
(2,'miguel', '$2a$10$27wkx.cJ8xJCGWVCY3dg/.OcnvhPWK9rQhMDDKWdJZBno77tyVrK6', 'app.vendix@gmail.com', 'Miguel', 'Alonso', '+34 682 63 52 31', TRUE, NOW());

INSERT INTO USUARIOS_ROLES(ID_USER,ID_ROL) VALUES (1,1),(2,2);

INSERT INTO CATEGORIAS (nombre, descripcion) VALUES
('Libros', 'Categoría dedicada a libros de todo tipo, incluyendo novelas, educación y más.'),
('Electrónica', 'Dispositivos y artículos electrónicos como teléfonos, computadoras, videjuegoscesta_productos_producto y accesorios.'),
('Moda', 'Ropa, calzado y accesorios de moda para todas las edades.'),
('Deportes', 'Equipamiento y artículos relacionados con el deporte y la actividad física.'),
('Automocion', 'Productos y accesorios para automóviles, motos y otros vehículos.'),
('Otros', 'Otro tipo de productos.');

INSERT INTO PRODUCTOS (nombre, descripcion, precio, stock, disponible, categoria_id, usuario_id, ruta_imagen) VALUES
('El Principito', 'Clásico de la literatura francesa', 12.99, 50, TRUE, 1, 2, 'libros/principito.jpg'),
('Cien años de soledad', 'Novela del premio Nobel Gabriel García Márquez', 18.50, 30, TRUE, 1, 2, 'libros/cien-anos.jpg'),
('Aprende SQL en 10 días', 'Guía práctica para aprender bases de datos', 29.99, 25, TRUE, 1, 2, 'libros/sql-10-dias.jpg'),

('iPhone 13', 'Smartphone de última generación', 899.00, 15, TRUE, 2, 2, 'electronica/iphone13.jpg'),
('Portátil HP Pavilion', 'Ordenador portátil 15.6" con 8GB RAM', 649.99, 10, TRUE, 2, 2, 'electronica/hp-pavilion.jpg'),
('Auriculares Sony WH-1000XM4', 'Auriculares inalámbricos con cancelación de ruido', 279.99, 20, TRUE, 2, 2, 'electronica/sony-wh1000xm4.jpg'),

('Camiseta básica blanca', 'Camiseta de algodón 100% unisex', 15.99, 100, TRUE, 3, 2, 'moda/camiseta-blanca.jpg'),
('Zapatillas Nike Air Max', 'Zapatillas deportivas de running', 89.99, 40, TRUE, 3, 2, 'moda/nike-airmax.jpg'),
('Reloj Casio Edifice', 'Reloj digital deportivo para hombre', 129.50, 25, TRUE, 3, 2, 'moda/casio-edifice.jpg'),

('Balón de fútbol Adidas', 'Balón oficial tamaño 5', 29.99, 30, TRUE, 4, 2, 'deportes/balon-adidas.jpg'),
('Raqueta de tenis Wilson', 'Raqueta profesional para adultos', 89.99, 15, TRUE, 4, 2, 'deportes/raqueta-wilson.jpg'),
('Bicicleta de montaña', 'Bicicleta MTB 21 velocidades', 349.99, 8, TRUE, 4, 2, 'deportes/bici-montana.jpg'),

('Aceite motor 5W-30', 'Aceite sintético 4L para motor', 34.99, 50, TRUE, 5, 2, 'automocion/aceite-5w30.jpg'),
('Kit de herramientas', 'Juego de 32 piezas para automóvil', 49.99, 20, TRUE, 5, 2, 'automocion/kit-herramientas.jpg'),
('Limpiaparabrisas', 'Juego de limpiaparabrisas delanteros', 19.99, 40, TRUE, 5, 2, 'automocion/limpiaparabrisas.jpg'),

('Mochila escolar', 'Mochila resistente con varios compartimentos', 24.99, 60, TRUE, 6, 2, 'otros/mochila-escolar.jpg'),
('Juego de tazas', 'Set de 4 tazas de cerámica', 18.50, 35, TRUE, 6, 2, 'otros/juego-tazas.jpg'),
('Peluche oso grande', 'Peluche suave de 50cm', 22.99, 20, TRUE, 6, 2, 'otros/peluche-oso.jpg');

-- Comentarios para productos de Libros (IDs 1-3)
INSERT INTO VALORACIONES_PRODUCTO (producto_id, usuario_id, comentario, valoracion) VALUES
-- Comentarios para 'El Principito' (ID 1)
(1, 2, 'Un clásico que todo el mundo debería leer. La historia es simple pero profunda.', 5),
(1, 2, 'La edición es muy bonita, con buenas ilustraciones. Lo recomiendo para niños y adultos.', 3),
(1, 2, 'Me llegó con una pequeña abolladura en la portada, pero por lo demás perfecto.', 3),

-- Comentarios para 'Cien años de soledad' (ID 2)
(2, 2, 'Obra maestra de la literatura latinoamericana. García Márquez es un genio.', 5),
(2, 2, 'La narrativa es compleja pero fascinante. Requiere atención para seguir los personajes.', 4),
(2, 2, 'El libro llegó con las páginas un poco amarillentas, parece una edición antigua.', 2),

-- Comentarios para 'Aprende SQL en 10 días' (ID 3)
(3, 2, 'Excelente para principiantes. Los ejemplos prácticos son muy útiles.', 4),
(3, 2, 'Buen contenido pero la traducción al español tiene algunos errores.', 3),
(3, 2, 'Esperaba más ejercicios avanzados, pero cumple para lo básico.', 3),

-- Comentarios para productos de Electrónica (IDs 4-6)
-- Comentarios para 'iPhone 13' (ID 4)
(4, 2, 'El mejor teléfono que he tenido. La cámara es espectacular.', 5),
(4, 2, 'Buen rendimiento pero la batería podría durar más.', 3),
(4, 2, 'Demasiado caro para lo que ofrece. Hay alternativas Android mejores.', 1),

-- Comentarios para 'Portátil HP Pavilion' (ID 5)
(5, 2, 'Muy buen rendimiento para el precio. Ideal para trabajo y estudios.', 5),
(5, 2, 'El teclado es cómodo pero la pantalla podría tener mejor brillo.', 3),
(5, 2, 'Se calienta bastante con uso intensivo. Necesita un cooler adicional.', 1),

-- Comentarios para 'Auriculares Sony WH-1000XM4' (ID 6)
(6, 2, 'La cancelación de ruido es increíble. Sonido de alta calidad.', 5),
(6, 2, 'Cómodos para largas sesiones pero un poco pesados.', 4),
(6, 2, 'La aplicación de configuración podría ser más intuitiva.', 3),

-- Comentarios para productos de Moda (IDs 7-9)
-- Comentarios para 'Camiseta básica blanca' (ID 7)
(7, 2, 'Muy buena calidad de tela. Se lava bien sin perder forma.', 5),
(7, 2, 'Básica pero versátil. El corte es un poco holgado.', 4),
(7, 2, 'Se transparenta un poco con el sudor. Necesita camiseta interior.', 3),

-- Comentarios para 'Zapatillas Nike Air Max' (ID 8)
(8, 2, 'Super cómodas para correr. Buen soporte para los pies.', 5),
(8, 2, 'Bonito diseño pero la suela se desgasta rápido en asfalto.', 4),
(8, 2, 'Tallan un poco grande. Recomiendo pedir media talla menos.', 3),

-- Comentarios para 'Reloj Casio Edifice' (ID 9)
(9, 2, 'Elegante y resistente. Perfecto para uso diario.', 5),
(9, 2, 'Las funciones son buenas pero la correa es un poco incómoda.', 4),
(9, 2, 'La pantalla es pequeña para mi gusto. Difícil de leer sin gafas.', 3),

-- Comentarios para productos de Deportes (IDs 10-12)
-- Comentarios para 'Balón de fútbol Adidas' (ID 10)
(10, 2, 'Excelente calidad. Buen rebote y agarre.', 5),
(10, 2, 'El material es resistente pero la costura podría ser mejor.', 4),
(10, 2, 'No es adecuado para superficies rugosas, solo para césped.', 3),

-- Comentarios para 'Raqueta de tenis Wilson' (ID 11)
(11, 2, 'Muy ligera y con buen control. Ideal para intermedios.', 5),
(11, 2, 'El grip incluido es de mala calidad. Hay que comprar uno aparte.', 4),
(11, 2, 'No viene con funda protectora. Debería incluirlo por el precio.', 3),

-- Comentarios para 'Bicicleta de montaña' (ID 12)
(12, 2, 'Suspensión excelente para terrenos difíciles. Muy resistente.', 5),
(12, 2, 'El montaje fue complicado. Las instrucciones no son claras.', 4),
(12, 2, 'Los frenos necesitan ajuste profesional desde el primer día.', 3),

-- Comentarios para productos de Automoción (IDs 13-15)
-- Comentarios para 'Aceite motor 5W-30' (ID 13)
(13, 2, 'Mi coche funciona mucho mejor desde que uso este aceite.', 5),
(13, 2, 'Buena relación calidad-precio. Cumple con lo esperado.', 4),
(13, 2, 'El envase es difícil de abrir sin derramar.', 3),

-- Comentarios para 'Kit de herramientas' (ID 14)
(14, 2, 'Muy completo. Incluye todo lo básico para reparaciones.', 5),
(14, 2, 'Las llaves son de buena calidad pero el maletín es frágil.', 4),
(14, 2, 'Faltan algunas piezas anunciadas en la descripción.', 3),

-- Comentarios para 'Limpiaparabrisas' (ID 15)
(15, 2, 'Funcionan perfectamente. Fácil instalación.', 5),
(15, 2, 'Buen rendimiento pero hacen un poco de ruido.', 4),
(15, 2, 'No se ajustan bien a mi modelo de coche como anunciaban.', 3),

-- Comentarios para productos Otros (IDs 16-18)
-- Comentarios para 'Mochila escolar' (ID 16)
(16, 2, 'Muy resistente y con mucho espacio. Ideal para estudiantes.', 5),
(16, 2, 'Los tirantes podrían ser más acolchados para mayor comodidad.', 4),
(16, 2, 'El color no coincide exactamente con la foto del producto.', 3),

-- Comentarios para 'Juego de tazas' (ID 17)
(17, 2, 'Bonito diseño y buena calidad de cerámica.', 5),
(17, 2, 'Una de las tazas llegó con un pequeño desconchado.', 4),
(17, 2, 'El tamaño es más pequeño de lo que esperaba.', 3),

-- Comentarios para 'Peluche oso grande' (ID 18)
(18, 2, 'Muy suave y perfecto para regalar a niños.', 5),
(18, 2, 'El relleno podría ser más consistente, se aplasta fácil.', 4),
(18, 2, 'El hilo de la costura parece de baja calidad.', 3);

insert into Cesta_Productos (id,total) Values (2,0);

insert into DIRECCION (id,ID_USER,calle,ciudad,provincia,CODIGO_POSTAL,pais) 
VALUES (1,2,"AV Antonio Hurtado", "Caceres","Caceres","10002","España");

ALTER TABLE CESTA_PRODUCTOS AUTO_INCREMENT = 2;


