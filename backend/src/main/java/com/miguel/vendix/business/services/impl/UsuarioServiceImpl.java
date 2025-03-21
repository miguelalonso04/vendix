package com.miguel.vendix.business.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguel.vendix.business.model.Direccion;
import com.miguel.vendix.security.model.Role;
import com.miguel.vendix.security.model.Usuario;
import com.miguel.vendix.business.services.UsuarioService;
import com.miguel.vendix.integration.repositories.DireccionRepository;
import com.miguel.vendix.security.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private DireccionRepository direccionRepository;
	
	@Override
	public Optional<Usuario> login(String email, String password) {
		return Optional.ofNullable(usuarioRepository.findByEmailAndPassword(email, password));
	}

	@Override
	public Long register(Usuario user) {
		
		if (usuarioRepository.findByUsername(user.getUsername()).isPresent()) {
		    throw new IllegalArgumentException("Username already exists");
		}
		
        if (usuarioRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        
       return usuarioRepository.save(user).getId();
	}

	@Override
	public Optional<Usuario> read(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Usuario> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long addDireccionUser(Long userId, Direccion direccion) throws Exception {
		Usuario user = usuarioRepository.findById(userId)
				.orElseThrow(() -> new Exception("Usuario no encontrado"));
		
		direccion.setUsuario(user);
		
		return direccionRepository.save(direccion).getId();
	}

	@Override
	public Optional<Role> findRolByIdUsuario(Long idUsuario) {
		return usuarioRepository.findRolByIdUsuario(idUsuario);
	}

	@Override
	public Optional<Usuario> findByUserName(String username) {
		return usuarioRepository.findByUsername(username);
	}

	@Override
	public boolean existsByEmail(String email) {
		return usuarioRepository.existsByEmail(email);
	}

}
