package com.miguel.vendix.presentation.restcontrollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miguel.vendix.business.model.Direccion;
import com.miguel.vendix.business.services.UsuarioService;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionController {

	private UsuarioService usuarioServices;
	
	public DireccionController(UsuarioService usuarioServices) {
		this.usuarioServices = usuarioServices;
	}
	
	 @PostMapping("/user/{userId}")
	    public ResponseEntity<String> addDireccion(@PathVariable Long userId, @RequestBody Direccion direccion) {
	        try {
	            usuarioServices.addDireccionUser(userId, direccion);
	            return ResponseEntity.ok("Direccion añadida correctamente");
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
	        }
	 }
}
