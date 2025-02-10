package com.miguel.vendix.presentation.restcontrollers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.miguel.vendix.business.model.Usuario;
import com.miguel.vendix.business.services.UsuarioServices;

@RestController
@RequestMapping("/api/users")
public class UsuarioController {

	private UsuarioServices usuarioServices;
	
	public UsuarioController(UsuarioServices usuarioServices) {
		this.usuarioServices = usuarioServices;
	}
	
	@GetMapping("/login")
	public ResponseEntity<?> login(@RequestParam(required = true) String email, @RequestParam(required = true) String password){
		
		Optional<Usuario> usuario = usuarioServices.login(email, password);
		
		if(usuario.isEmpty())
			throw new IllegalStateException("Login incorrecto.");
		
		return ResponseEntity.ok(usuario);
	}
	
	@PostMapping("/registrar")
	public ResponseEntity<?> registrar(@RequestParam(required = true) Usuario user, UriComponentsBuilder ucb) throws Exception{
		
		Long id = usuarioServices.register(user);
		
		return ResponseEntity.created(ucb.path("/users/{id}").build(id)).build();
	}
}
