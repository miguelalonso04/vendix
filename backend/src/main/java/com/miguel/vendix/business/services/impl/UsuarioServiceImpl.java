package com.miguel.vendix.business.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguel.vendix.business.model.Direccion;
import com.miguel.vendix.business.model.Usuario;
import com.miguel.vendix.business.services.UsuarioService;
import com.miguel.vendix.integration.repositories.DireccionRepository;
import com.miguel.vendix.integration.repositories.UsuarioRepository;

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
	public Long register(Usuario user) throws Exception {
		
		if (usuarioRepository.existByUserName(user.getUserName())) {
            throw new Exception("Username already exists");
        }
        if (usuarioRepository.existsByEmail(user.getEmail())) {
            throw new Exception("Email already exists");
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

}
