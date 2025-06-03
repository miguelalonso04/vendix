-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: vendixbbdd
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `descripcion` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'Libros','Categoría dedicada a libros de todo tipo, incluyendo novelas, educación y más.'),(2,'Electrónica','Dispositivos y artículos electrónicos como teléfonos, computadoras, videjuegoscesta_productos_producto y accesorios.'),(3,'Moda','Ropa, calzado y accesorios de moda para todas las edades.'),(4,'Deportes','Equipamiento y artículos relacionados con el deporte y la actividad física.'),(5,'Automocion','Productos y accesorios para automóviles, motos y otros vehículos.'),(6,'Otros','Otro tipo de productos.');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cesta_productos`
--

DROP TABLE IF EXISTS `cesta_productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cesta_productos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `total` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cesta_productos`
--

LOCK TABLES `cesta_productos` WRITE;
/*!40000 ALTER TABLE `cesta_productos` DISABLE KEYS */;
INSERT INTO `cesta_productos` VALUES (2,0);
/*!40000 ALTER TABLE `cesta_productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cesta_productos_producto`
--

DROP TABLE IF EXISTS `cesta_productos_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cesta_productos_producto` (
  `cesta_id` bigint NOT NULL,
  `producto_id` bigint NOT NULL,
  `cantidad` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`cesta_id`,`producto_id`),
  KEY `producto_id` (`producto_id`),
  CONSTRAINT `cesta_productos_producto_ibfk_1` FOREIGN KEY (`cesta_id`) REFERENCES `cesta_productos` (`id`),
  CONSTRAINT `cesta_productos_producto_ibfk_2` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cesta_productos_producto`
--

LOCK TABLES `cesta_productos_producto` WRITE;
/*!40000 ALTER TABLE `cesta_productos_producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `cesta_productos_producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `direccion`
--

DROP TABLE IF EXISTS `direccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `direccion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ID_USER` bigint DEFAULT NULL,
  `calle` varchar(255) NOT NULL,
  `ciudad` varchar(255) NOT NULL,
  `provincia` varchar(255) NOT NULL,
  `CODIGO_POSTAL` varchar(20) NOT NULL,
  `pais` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ID_USER` (`ID_USER`),
  CONSTRAINT `direccion_ibfk_1` FOREIGN KEY (`ID_USER`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `direccion`
--

LOCK TABLES `direccion` WRITE;
/*!40000 ALTER TABLE `direccion` DISABLE KEYS */;
INSERT INTO `direccion` VALUES (1,2,'AV Antonio Hurtado','Caceres','Caceres','10002','España');
/*!40000 ALTER TABLE `direccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido_productos`
--

DROP TABLE IF EXISTS `pedido_productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido_productos` (
  `pedido_id` bigint NOT NULL,
  `producto_id` bigint NOT NULL,
  `cantidad` int NOT NULL,
  PRIMARY KEY (`pedido_id`,`producto_id`),
  KEY `producto_id` (`producto_id`),
  CONSTRAINT `pedido_productos_ibfk_1` FOREIGN KEY (`pedido_id`) REFERENCES `pedidos` (`id`),
  CONSTRAINT `pedido_productos_ibfk_2` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido_productos`
--

LOCK TABLES `pedido_productos` WRITE;
/*!40000 ALTER TABLE `pedido_productos` DISABLE KEYS */;
/*!40000 ALTER TABLE `pedido_productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedidos`
--

DROP TABLE IF EXISTS `pedidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedidos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cesta_id` bigint DEFAULT NULL,
  `direccion_id` bigint DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  `precioTotal` double DEFAULT NULL,
  `estado` varchar(20) DEFAULT NULL,
  `fechaPedido` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `direccion_id` (`direccion_id`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`direccion_id`) REFERENCES `direccion` (`id`),
  CONSTRAINT `pedidos_ibfk_2` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedidos`
--

LOCK TABLES `pedidos` WRITE;
/*!40000 ALTER TABLE `pedidos` DISABLE KEYS */;
/*!40000 ALTER TABLE `pedidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `descripcion` text,
  `precio` double NOT NULL,
  `stock` int NOT NULL DEFAULT '0',
  `disponible` tinyint(1) NOT NULL DEFAULT '1',
  `categoria_id` bigint DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  `ruta_imagen` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `categoria_id` (`categoria_id`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`id`),
  CONSTRAINT `productos_ibfk_2` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,'El Principito','Clásico de la literatura francesa',12.99,50,1,1,2,'libros/principito.jpg'),(2,'Cien años de soledad','Novela del premio Nobel Gabriel García Márquez',18.5,30,1,1,2,'libros/cien-anos.jpg'),(3,'Aprende SQL en 10 días','Guía práctica para aprender bases de datos',29.99,25,1,1,2,'libros/sql-10-dias.jpg'),(4,'iPhone 13','Smartphone de última generación',899,15,1,2,2,'electronica/iphone13.jpg'),(5,'Portátil HP Pavilion','Ordenador portátil 15.6\" con 8GB RAM',649.99,10,1,2,2,'electronica/hp-pavilion.jpg'),(6,'Auriculares Sony WH-1000XM4','Auriculares inalámbricos con cancelación de ruido',279.99,20,1,2,2,'electronica/sony-wh1000xm4.jpg'),(7,'Camiseta básica blanca','Camiseta de algodón 100% unisex',15.99,100,1,3,2,'moda/camiseta-blanca.jpg'),(8,'Zapatillas Nike Air Max','Zapatillas deportivas de running',89.99,40,1,3,2,'moda/nike-airmax.jpg'),(9,'Reloj Casio Edifice','Reloj digital deportivo para hombre',129.5,25,1,3,2,'moda/casio-edifice.jpg'),(10,'Balón de fútbol Adidas','Balón oficial tamaño 5',29.99,30,1,4,2,'deportes/balon-adidas.jpg'),(11,'Raqueta de tenis Wilson','Raqueta profesional para adultos',89.99,15,1,4,2,'deportes/raqueta-wilson.jpg'),(12,'Bicicleta de montaña','Bicicleta MTB 21 velocidades',349.99,8,1,4,2,'deportes/bici-montana.jpg'),(13,'Aceite motor 5W-30','Aceite sintético 4L para motor',34.99,50,1,5,2,'automocion/aceite-5w30.jpg'),(14,'Kit de herramientas','Juego de 32 piezas para automóvil',49.99,20,1,5,2,'automocion/kit-herramientas.jpg'),(15,'Limpiaparabrisas','Juego de limpiaparabrisas delanteros',19.99,40,1,5,2,'automocion/limpiaparabrisas.jpg'),(16,'Mochila escolar','Mochila resistente con varios compartimentos',24.99,60,1,6,2,'otros/mochila-escolar.jpg'),(17,'Juego de tazas','Set de 4 tazas de cerámica',18.5,35,1,6,2,'otros/juego-tazas.jpg'),(18,'Peluche oso grande','Peluche suave de 50cm',22.99,20,1,6,2,'otros/peluche-oso.jpg');
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tipo` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ADMIN'),(2,'USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `FIRST_NAME` varchar(255) DEFAULT NULL,
  `LAST_NAME` varchar(255) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `lastPasswordResetDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'admin','$2a$10$27wkx.cJ8xJCGWVCY3dg/.OcnvhPWK9rQhMDDKWdJZBno77tyVrK6','admin@gmail.com','Miguel','Alonso','+34 634 12 31 23',1,'2025-05-28 10:33:40'),(2,'miguel','$2a$10$27wkx.cJ8xJCGWVCY3dg/.OcnvhPWK9rQhMDDKWdJZBno77tyVrK6','app.vendix@gmail.com','Miguel','Alonso','+34 682 63 52 31',1,'2025-05-28 10:33:40');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios_roles`
--

DROP TABLE IF EXISTS `usuarios_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios_roles` (
  `ID_USER` bigint NOT NULL,
  `ID_ROL` bigint NOT NULL,
  PRIMARY KEY (`ID_USER`,`ID_ROL`),
  KEY `ID_ROL` (`ID_ROL`),
  CONSTRAINT `usuarios_roles_ibfk_1` FOREIGN KEY (`ID_USER`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `usuarios_roles_ibfk_2` FOREIGN KEY (`ID_ROL`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios_roles`
--

LOCK TABLES `usuarios_roles` WRITE;
/*!40000 ALTER TABLE `usuarios_roles` DISABLE KEYS */;
INSERT INTO `usuarios_roles` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `usuarios_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `valoraciones_producto`
--

DROP TABLE IF EXISTS `valoraciones_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `valoraciones_producto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `producto_id` bigint DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  `comentario` text,
  `valoracion` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `producto_id` (`producto_id`),
  KEY `usuario_id` (`usuario_id`),
  CONSTRAINT `valoraciones_producto_ibfk_1` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`),
  CONSTRAINT `valoraciones_producto_ibfk_2` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `valoraciones_producto`
--

LOCK TABLES `valoraciones_producto` WRITE;
/*!40000 ALTER TABLE `valoraciones_producto` DISABLE KEYS */;
INSERT INTO `valoraciones_producto` VALUES (1,1,2,'Un clásico que todo el mundo debería leer. La historia es simple pero profunda.',5),(2,1,2,'La edición es muy bonita, con buenas ilustraciones. Lo recomiendo para niños y adultos.',3),(3,1,2,'Me llegó con una pequeña abolladura en la portada, pero por lo demás perfecto.',3),(4,2,2,'Obra maestra de la literatura latinoamericana. García Márquez es un genio.',5),(5,2,2,'La narrativa es compleja pero fascinante. Requiere atención para seguir los personajes.',4),(6,2,2,'El libro llegó con las páginas un poco amarillentas, parece una edición antigua.',2),(7,3,2,'Excelente para principiantes. Los ejemplos prácticos son muy útiles.',4),(8,3,2,'Buen contenido pero la traducción al español tiene algunos errores.',3),(9,3,2,'Esperaba más ejercicios avanzados, pero cumple para lo básico.',3),(10,4,2,'El mejor teléfono que he tenido. La cámara es espectacular.',5),(11,4,2,'Buen rendimiento pero la batería podría durar más.',3),(12,4,2,'Demasiado caro para lo que ofrece. Hay alternativas Android mejores.',1),(13,5,2,'Muy buen rendimiento para el precio. Ideal para trabajo y estudios.',5),(14,5,2,'El teclado es cómodo pero la pantalla podría tener mejor brillo.',3),(15,5,2,'Se calienta bastante con uso intensivo. Necesita un cooler adicional.',1),(16,6,2,'La cancelación de ruido es increíble. Sonido de alta calidad.',5),(17,6,2,'Cómodos para largas sesiones pero un poco pesados.',4),(18,6,2,'La aplicación de configuración podría ser más intuitiva.',3),(19,7,2,'Muy buena calidad de tela. Se lava bien sin perder forma.',5),(20,7,2,'Básica pero versátil. El corte es un poco holgado.',4),(21,7,2,'Se transparenta un poco con el sudor. Necesita camiseta interior.',3),(22,8,2,'Super cómodas para correr. Buen soporte para los pies.',5),(23,8,2,'Bonito diseño pero la suela se desgasta rápido en asfalto.',4),(24,8,2,'Tallan un poco grande. Recomiendo pedir media talla menos.',3),(25,9,2,'Elegante y resistente. Perfecto para uso diario.',5),(26,9,2,'Las funciones son buenas pero la correa es un poco incómoda.',4),(27,9,2,'La pantalla es pequeña para mi gusto. Difícil de leer sin gafas.',3),(28,10,2,'Excelente calidad. Buen rebote y agarre.',5),(29,10,2,'El material es resistente pero la costura podría ser mejor.',4),(30,10,2,'No es adecuado para superficies rugosas, solo para césped.',3),(31,11,2,'Muy ligera y con buen control. Ideal para intermedios.',5),(32,11,2,'El grip incluido es de mala calidad. Hay que comprar uno aparte.',4),(33,11,2,'No viene con funda protectora. Debería incluirlo por el precio.',3),(34,12,2,'Suspensión excelente para terrenos difíciles. Muy resistente.',5),(35,12,2,'El montaje fue complicado. Las instrucciones no son claras.',4),(36,12,2,'Los frenos necesitan ajuste profesional desde el primer día.',3),(37,13,2,'Mi coche funciona mucho mejor desde que uso este aceite.',5),(38,13,2,'Buena relación calidad-precio. Cumple con lo esperado.',4),(39,13,2,'El envase es difícil de abrir sin derramar.',3),(40,14,2,'Muy completo. Incluye todo lo básico para reparaciones.',5),(41,14,2,'Las llaves son de buena calidad pero el maletín es frágil.',4),(42,14,2,'Faltan algunas piezas anunciadas en la descripción.',3),(43,15,2,'Funcionan perfectamente. Fácil instalación.',5),(44,15,2,'Buen rendimiento pero hacen un poco de ruido.',4),(45,15,2,'No se ajustan bien a mi modelo de coche como anunciaban.',3),(46,16,2,'Muy resistente y con mucho espacio. Ideal para estudiantes.',5),(47,16,2,'Los tirantes podrían ser más acolchados para mayor comodidad.',4),(48,16,2,'El color no coincide exactamente con la foto del producto.',3),(49,17,2,'Bonito diseño y buena calidad de cerámica.',5),(50,17,2,'Una de las tazas llegó con un pequeño desconchado.',4),(51,17,2,'El tamaño es más pequeño de lo que esperaba.',3),(52,18,2,'Muy suave y perfecto para regalar a niños.',5),(53,18,2,'El relleno podría ser más consistente, se aplasta fácil.',4),(54,18,2,'El hilo de la costura parece de baja calidad.',3);
/*!40000 ALTER TABLE `valoraciones_producto` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-29 10:30:17
