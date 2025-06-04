package com.miguel.vendix.business.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class LocalStorageService {

    @Value("${ruta.imagenes.productos}")
    private String uploadDir; // Ruta configurada en application.properties

    public String guardarImagen(MultipartFile archivo, Long productoId) throws IOException {
        // Genera un nombre único para el archivo (ej: "producto_1_aj8sd9.jpg")
        String nombreArchivo = "producto_" + productoId + "_" + UUID.randomUUID() + ".jpg";
        
        // Ruta completa donde se guardará la imagen
        Path rutaCompleta = Paths.get(uploadDir + nombreArchivo);
        
        System.out.println(rutaCompleta);
        
        // Crea el directorio si no existe
        //Files.createDirectories(rutaCompleta.getParent());
        
        // Guarda el archivo en disco
        Files.write(rutaCompleta, archivo.getBytes());
        
        // Devuelve la ruta relativa (ej: "productos/producto_1_aj8sd9.jpg")
        return "productos/" + nombreArchivo;
    }
}
