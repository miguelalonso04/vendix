package com.miguel.vendix.security.restcontrollers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miguel.vendix.business.model.Direccion;
import com.miguel.vendix.business.services.UsuarioService;
import com.miguel.vendix.presentation.config.PresentationException;
import com.miguel.vendix.security.model.Role;
import com.miguel.vendix.security.model.Usuario;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public List<Usuario> getAll(){
		return usuarioService.getAll();
	}
	
	@GetMapping("/{id}")
	public Usuario read(@PathVariable Long id){
		
		Optional<Usuario> optional = usuarioService.read(id);
		
		if(optional.isEmpty()) {
			throw new PresentationException("No existe el usuario con id " + id, HttpStatus.NOT_FOUND);
		}
		
		return optional.get();
		
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		try {
			usuarioService.delete(id);
		} catch(IllegalStateException e) {
			throw new PresentationException("El usuario con ID [" + id + "] no existe.", HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{id}/deshabilitar")
	public void deshabilitarUsuario(@PathVariable Long id) {
		try {
			usuarioService.deshabilitarUsuario(id);
		} catch(IllegalStateException e) {
			throw new PresentationException("El usuario con ID [" + id + "] no existe.", HttpStatus.NOT_FOUND);
		}
	}
	

	@PutMapping("/{id}/habilitar")
	public void habilitarUsuario(@PathVariable Long id) {
		try {
			usuarioService.habilitarUsuario(id);
		} catch(IllegalStateException e) {
			throw new PresentationException("El usuario con ID [" + id + "] no existe.", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{idUser}/direccion")
	public Direccion getDireccion(@PathVariable Long idUser,@RequestParam Long idDireccion) {
		
		Optional <Direccion> optional = usuarioService.getDireccion(idUser, idDireccion);
		
		if(optional.isEmpty()) {
			throw new PresentationException("No existe la direccion asociada al usuario con id " + idUser, HttpStatus.NOT_FOUND);
		}
		
		return optional.get();
	}
	
	@GetMapping("/{idUser}/direcciones")
	public List<Direccion> getAllDirecciones(@PathVariable Long idUser){		
		return usuarioService.getAllDirecciones(idUser);
	}
	
	@PutMapping("/{id}/direccion")
	public ResponseEntity<?> addDireccion(@PathVariable Long id, @RequestBody Direccion direccion){
				
		try {
			usuarioService.addDireccionUser(id, direccion);
		} catch(Exception e) {
			throw new PresentationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@GetMapping("/{id}/rol")
	public Role getRol(@PathVariable Long id) {
		Optional<Role> optional = usuarioService.findRolByIdUsuario(id);
		
		if(optional.isEmpty()) {
			throw new PresentationException("No existe el rol con id " + id, HttpStatus.NOT_FOUND);
		}
		
		return optional.get();
	}
	
	@GetMapping("/username")
	public Usuario getUserByUsername(@RequestParam String username) {
		
		Optional<Usuario> optional = usuarioService.findByUserName(username);
		
		if(optional.isEmpty()) {
			throw new PresentationException("No existe el usuario "+username, HttpStatus.NOT_FOUND);
		}
		
		return optional.get();
		
	}
	
	@DeleteMapping("/direccion/{idDireccion}")
	public void deleteDireccion(@PathVariable Long idDireccion) {
		try {
			usuarioService.deleteDireccion(idDireccion);
		} catch(IllegalStateException e) {
			throw new PresentationException("La direccion con ID [ "+idDireccion+" ] no existe", HttpStatus.NOT_FOUND);
		}
	}
	
	
}
