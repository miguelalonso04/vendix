package com.miguel.vendix.presentation.restcontrollers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miguel.vendix.business.model.CestaProductos;
import com.miguel.vendix.business.model.Producto;
import com.miguel.vendix.business.services.CestaProductoService;
import com.miguel.vendix.presentation.config.PresentationException;

@RestController
@RequestMapping("/api/cesta")
public class CestaProductosController {
	
	@Autowired
	private CestaProductoService cestaService;
	
	@GetMapping("/{id}")
	public CestaProductos getCesta(@PathVariable Long id){
		
		Optional<CestaProductos> optional = cestaService.read(id);
		
		if(optional.isEmpty()) {
			throw new PresentationException("No existe La cesta con id " + id, HttpStatus.NOT_FOUND);
		}
		
		return optional.get();
	}
	
	@PostMapping("/{idCesta}/añadir")
    public ResponseEntity<?> añadirProductoACesta(@RequestBody Producto producto, @PathVariable Long idCesta) {
        cestaService.añadirProductoACesta(producto, idCesta);
        return ResponseEntity.ok().build();
    }
}
