package com.miguel.vendix.presentation.restcontrollers;

import java.util.List;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.miguel.vendix.business.model.Producto;
import com.miguel.vendix.business.services.ProductoService;
import com.miguel.vendix.presentation.config.PresentationException;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

	private ProductoService productoService;
	
	public ProductoController(ProductoService productosServices) {
		this.productoService = productosServices;
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
	public ResponseEntity<?> createProducto(@RequestBody Producto producto, UriComponentsBuilder ucb){
		
		Long id = null;
		
		try {
			id = productoService.create(producto);
		} catch(IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.created(ucb.path("/productos/{id}").build(id)).build();
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
	
	
}
