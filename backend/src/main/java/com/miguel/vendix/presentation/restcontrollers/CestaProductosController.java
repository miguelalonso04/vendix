package com.miguel.vendix.presentation.restcontrollers;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miguel.vendix.business.model.CestaProductos;
import com.miguel.vendix.business.model.Producto;
import com.miguel.vendix.business.services.CestaProductoService;
import com.miguel.vendix.business.services.ProductoService;
import com.miguel.vendix.presentation.config.PresentationException;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/cesta")
public class CestaProductosController {
	
	@Autowired
	private CestaProductoService cestaService;
	
	@Autowired
	private ProductoService productoService;
	
	@GetMapping("/{id}")
	public CestaProductos getCesta(@PathVariable Long id){
		
		Optional<CestaProductos> optional = cestaService.read(id);
		
		if(optional.isEmpty()) {
			throw new PresentationException("No existe La cesta con id " + id, HttpStatus.NOT_FOUND);
		}
		
		return optional.get();
	}
	
	@PutMapping("/{idCesta}/añadir")
	@Transactional
    public ResponseEntity<?> añadirProductoACesta(@RequestBody Producto producto, @PathVariable Long idCesta) {
		
		try {
			cestaService.añadirProductoACesta(producto, idCesta);
		} catch(IllegalStateException e) {
			throw new PresentationException("NO HAY SUFICIENTE STOCK DE "+producto.getNombre(), HttpStatus.BAD_REQUEST);
		}
        return ResponseEntity.ok().build();
    }

	@PutMapping("/vaciar/{idCesta}")
	public  void vaciarCesta(@PathVariable Long idCesta){
	
		try {
			cestaService.vaciarCesta(idCesta);
		} catch(IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}	
	}
	
	@PutMapping("/eliminar/{idProducto}")
	public void eliminarUnProductoEnCesta(@PathVariable Long idProducto, @RequestParam Long idCesta) {
		
		try {
			cestaService.eliminarUnProductoEnCesta(idProducto, idCesta);
		} catch(IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}	
	}
	
	@GetMapping("/productos")
	public Map<Producto,Integer> getAllProductos(@RequestParam Long idCesta){
		
		Optional<CestaProductos> optional = cestaService.read(idCesta);
		
		if(optional.isEmpty()) {
			throw new PresentationException("No existe La cesta con id " + idCesta, HttpStatus.NOT_FOUND);
		}
		
		if(optional.get().getProductos().isEmpty()) {
			throw new PresentationException("Tu cesta esta vacia",HttpStatus.NO_CONTENT );
		}
		
		return cestaService.getAllProductos(idCesta);
		
	}
	
	@GetMapping("/productos/precio/{idProducto}")
	public Double getPrecioXProducto(@PathVariable Long idProducto, @RequestParam Long idCesta) {
		
		Optional<CestaProductos> cesta = cestaService.read(idCesta);
		
		Optional<Producto> producto = productoService.read(idProducto);
		
		if(cesta.isEmpty()) {
			throw new PresentationException("No existe La cesta con id " + idCesta, HttpStatus.NOT_FOUND);
		}
		
		if(producto.isEmpty()) {
			throw new PresentationException("No existe el producto con id " + idProducto, HttpStatus.NOT_FOUND);
		}
		
		return cestaService.getPrecioXProducto(idProducto, idCesta);
	}
	
	@GetMapping("/productos/precio")
	public Double getPrecioTotalCesta(@RequestParam Long idCesta) {
		
		Optional<CestaProductos> optional = cestaService.read(idCesta);
		
		if(optional.isEmpty()) {
			throw new PresentationException("No existe La cesta con id " + idCesta, HttpStatus.NOT_FOUND);
		}
		
		if(optional.get().getProductos().isEmpty()) {
			throw new PresentationException("Tu cesta esta vacia",HttpStatus.NO_CONTENT );
		}
		
		return cestaService.calcularTotalCesta(idCesta);
	}
	
	@PutMapping("/productos/sumar/{idProducto}")
	public void sumarCantidadXProducto(@PathVariable Long idProducto, @RequestParam Long idCesta) {
		
		try {
			cestaService.sumarCantidadXProducto(idProducto, idCesta);
		} catch (IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping("/productos/restar/{idProducto}")
	public void restarCantidadXProducto(@PathVariable Long idProducto, @RequestParam Long idCesta) {
		
		try {
			cestaService.restarCanitdadXProducto(idProducto, idCesta);
		} catch (IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
}