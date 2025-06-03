package com.miguel.vendix.presentation.restcontrollers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.miguel.vendix.business.model.Producto;
import com.miguel.vendix.business.services.ProductoService;
import com.miguel.vendix.business.services.impl.LocalStorageService;
import com.miguel.vendix.presentation.config.PresentationException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

	private ProductoService productoService;
	
    private LocalStorageService localStorageService;
	
	public ProductoController(ProductoService productosServices, LocalStorageService localStorageService) {
		this.productoService = productosServices;
		this.localStorageService = localStorageService;
	}
	
	@GetMapping
	public List<Producto> getAll(){
		return productoService.getAll();
	}
	
	@GetMapping("/{id}")
	public Producto getProducto(@PathVariable Long id) {
		
		Optional<Producto> optional = productoService.read(id);
		
		if(optional.isEmpty()) {
			throw new PresentationException("No existe el producto con id " + id, HttpStatus.NOT_FOUND);
		}
		
		return optional.get();
	}
	
	@PostMapping
	public ResponseEntity<?> createProducto(@RequestBody Producto producto, @RequestParam Long idUsuario, UriComponentsBuilder ucb){
		
		Long id = null;
		
		try {
			id = productoService.create(producto,idUsuario);
		} catch(IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.created(ucb.path("/productos/{id}").build(id)).body(id);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateProducto(@RequestBody Producto producto, @PathVariable Long id) {
		
		producto.setId(id);
		
		try {
			productoService.update(producto);
		} catch(IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}	
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProducto(@PathVariable Long id){
		
		try {
			productoService.delete(id);
		} catch(IllegalStateException e) {
			throw new PresentationException("El producto con ID [" + id + "] no existe.", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/precio")
	public List<Producto> getBetweenPrinceRange(@RequestParam double min, @RequestParam double max) {
		List<Producto> lista = productoService.getBetweenPriceRange(min, max);
		
		if(lista.isEmpty()) {
			throw new PresentationException("No existen productos entre [ "+min+"€ - "+max+"€ ].", HttpStatus.NO_CONTENT);
		}
		
		return lista;
	}
	
	
    @GetMapping("/totalCount")
    public int getNumeroTotalProductos() {
        return productoService.getNumeroTotalProductos();
    }
	
    
    @GetMapping("/categoria")
    public List<Producto> getAllByCategoria(@RequestParam Long idCategoria){
    	
    	List<Producto> lista = productoService.getlAllByCategoria(idCategoria);
		
		if(lista.isEmpty()) {
			throw new PresentationException("No existen productos para la Categoria con id [ "+idCategoria+" ].", HttpStatus.NO_CONTENT);
		}
		
		return lista;
    }
	
    @GetMapping("/nombre/{nombreProducto}")
    public List<Producto> getAllByNombre(@PathVariable String nombreProducto){
    	
    	if(nombreProducto == null || nombreProducto.isEmpty()) {
    		throw new PresentationException("No se ha recibido ningun nombre", HttpStatus.BAD_REQUEST);
    	}
    	
    	List<Producto> lista = productoService.getAllByNombre(nombreProducto);
    	
    	if(lista.isEmpty()) {
    		throw new PresentationException("No existe ningun producto que empiece por ["+nombreProducto+"].", HttpStatus.NO_CONTENT);
    	}
    	
    	return lista;
    	
    }
    
    @PostMapping("/{idProducto}/upload-image")
    public ResponseEntity<Map<String, String>> uploadImage(
        @PathVariable Long idProducto,
        @RequestParam("imagen") MultipartFile imagen,  // Añade el nombre del parámetro
        HttpServletRequest request  // Para construir URLs dinámicas
    ) throws IOException {
        // 1. Validación básica del archivo
        if (imagen.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El archivo está vacío"));
        }

        // 2. Guarda la imagen (ajusta la ruta según entorno)
       
        String rutaImagen = localStorageService.guardarImagen(imagen, idProducto);

        // 3. Actualiza la BD
        productoService.actualizarRutaImagen(idProducto, rutaImagen);

        // 4. Devuelve URL completa (no solo la ruta relativa)
        String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String imagenUrl = baseUrl + "/uploads/" + rutaImagen;

        return ResponseEntity.ok(Map.of(
            "rutaImagen", rutaImagen,
            "urlCompleta", imagenUrl
        ));
    }
    
    @GetMapping("{idProducto}/imagen")
    public ResponseEntity<String> getImagen(@PathVariable Long idProducto) {
        String rutaImagen = productoService.getRutaImagen(idProducto);
        if (rutaImagen != null) {
            // Asume siempre HTTPS y dominio de Railway sin puerto
            String imagenUrl = "https://vendixx.up.railway.app/uploads/" + rutaImagen;
            return ResponseEntity.ok(imagenUrl);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public List<Producto> getProductosByUsuario(@PathVariable Long usuarioId) {
    	
    	if(usuarioId == null) {
    		throw new PresentationException("El id del usuario no puede ser null", HttpStatus.BAD_REQUEST);
    	}
    	
        List<Producto> productos = productoService.getAllByUsuario(usuarioId);
        
        if(productos.isEmpty()) {
        	throw new PresentationException("El usuario con id: ["+usuarioId+"]. No tiene ningun producto.", HttpStatus.NO_CONTENT);
        }
        return productos;
    }
    
}
