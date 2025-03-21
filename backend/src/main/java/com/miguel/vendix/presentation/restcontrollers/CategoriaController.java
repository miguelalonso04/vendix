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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.miguel.vendix.business.model.Categoria;
import com.miguel.vendix.business.services.CategoriaService;
import com.miguel.vendix.presentation.config.PresentationException;

@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	public List<Categoria> getAll(){
		return categoriaService.getAll();
	}
	
	@GetMapping("/{id}")
	public Categoria getCategoria(@PathVariable Long id) {
		
		Optional <Categoria> optional = categoriaService.read(id);
		
		if(optional.isEmpty()) {
			throw new PresentationException("No existe la categoria con id " + id, HttpStatus.NOT_FOUND);
		}
		
		return optional.get();
	}
	
	@PostMapping
	public ResponseEntity<?> createCategoria(@RequestBody Categoria categoria, UriComponentsBuilder ucb){
		
		Long id = null;
		
		try {
			id = categoriaService.create(categoria);
		} catch(IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.created(ucb.path("/categoria/{id}").build(id)).build();
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateCategoria(@RequestBody Categoria categoria, @PathVariable Long id) {
		
		categoria.setId(id);
		
		try {
			categoriaService.update(categoria);
		} catch (IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCategoria(@PathVariable Long id){
		
		try {
			categoriaService.delete(id);
		} catch(IllegalStateException e) {
			throw new PresentationException("La categoria con ID [" + id + "] no existe.", HttpStatus.NOT_FOUND);
		}
	}
}
