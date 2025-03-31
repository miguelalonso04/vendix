package com.miguel.vendix.presentation.restcontrollers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.miguel.vendix.business.model.ValoracionesProducto;
import com.miguel.vendix.business.services.ValoracionesProductoService;
import com.miguel.vendix.presentation.config.PresentationException;

@RestController
@RequestMapping("/api/valoraciones")
public class ValoracionesProductoController {

	@Autowired
	private ValoracionesProductoService valoracionesService;
	
	@GetMapping
	public List<ValoracionesProducto> getAllValoraciones(){
		return valoracionesService.getAll();
	}
	
	@GetMapping("/{id}")
	public ValoracionesProducto getValoracion(@PathVariable Long id) {
		
		Optional<ValoracionesProducto> optional = valoracionesService.read(id);
		
		if(optional.isEmpty()) {
			throw new PresentationException("No existe la valoracion con id " + id, HttpStatus.NOT_FOUND);
		}
		
		return optional.get();
	}
	
	@PostMapping
	public ResponseEntity<?> createValoracion(@RequestBody ValoracionesProducto valoracion,@PathVariable Long idProducto, @RequestParam Long idUsuario, UriComponentsBuilder ucb){
		
		Long id = null;
		
		try {
			id = valoracionesService.create(valoracion, idProducto, idUsuario);
		} catch(IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.created(ucb.path("/valoraciones/{id}").build(id)).build();		
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateProducto(@RequestBody ValoracionesProducto valoracion, @PathVariable Long id) {
		
		valoracion.setId(id);
		
		try {
			valoracionesService.update(valoracion);
		} catch(IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}	
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProducto(@PathVariable Long id){
		
		try {
			valoracionesService.delete(id);
		} catch(IllegalStateException e) {
			throw new PresentationException("La valoracion con ID [" + id + "] no existe.", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/productos/{id}")
	public List<ValoracionesProducto> getAllByProducto(@PathVariable Long id){
		return valoracionesService.getAllByProducto(id);
	}
	
	@GetMapping("/productos")
	public List<ValoracionesProducto> getAllByUserName(@RequestParam String username){
		return valoracionesService.getAllByUsername(username);
	}
	
	@GetMapping("/productos/{id}/count")
	public Integer numeroValoracionesXProducto(@PathVariable Long id) {
		return valoracionesService.numeroValoracionesXProducto(id);
	}
	
	@GetMapping("/productos/{id}/media")
	public Double mediaValoracionesXProducto(@PathVariable Long id) {
		return valoracionesService.mediaValoracionesXProducto(id);
	}
}
