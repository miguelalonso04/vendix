package com.miguel.vendix.business.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.miguel.vendix.business.model.Direccion;
import com.miguel.vendix.security.model.Role;
import com.miguel.vendix.security.model.Usuario;
import com.miguel.vendix.business.services.UsuarioService;
import com.miguel.vendix.integration.repositories.DireccionRepository;
import com.miguel.vendix.integration.repositories.PedidoRepository;
import com.miguel.vendix.security.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private DireccionRepository direccionRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
	public Optional<Usuario> login(String email, String password) {
		Usuario usuario = usuarioRepository.findByEmailAndPassword(email, password);

	    if (usuario == null) {
	        return Optional.empty();
	    }

	    if (!usuario.isEnabled()) {
	        System.out.println("El usuario existe pero no está activo.");
	        return Optional.empty();
	    }

	    return Optional.of(usuario);
	}

	@Override
	public Long register(Usuario user) {
		
		if (usuarioRepository.findByUsername(user.getUsername()).isPresent()) {
		    new IllegalArgumentException("Username already exists");
		}
		
        if (usuarioRepository.existsByEmail(user.getEmail())) {
            new IllegalArgumentException("Email already exists");
        }
        
       return usuarioRepository.save(user).getId();
	}

	@Override
	public Optional<Usuario> read(Long id) {
		
		Optional <Usuario> optional = usuarioRepository.findById(id);
		
		return optional.isEmpty() ? Optional.empty() : Optional.of(optional.get());
	}

	@Override
	public List<Usuario> getAll() {
		return usuarioRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		
		boolean existe = usuarioRepository.existsById(id);
		
		if(!existe) {
			new IllegalStateException("El usuario con ID [ "+id+" ] no existe");
		}
		
		Optional <Usuario> optional = usuarioRepository.findById(id);
		
		usuarioRepository.delete(optional.get());
		
	}
	
	@Override
	public void deshabilitarUsuario(Long id) {
		
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new IllegalStateException("El usuario con ID [ "+id+" ] no existe"));
		
		usuario.setEnabled(false);
		
		usuarioRepository.save(usuario);
	}

	@Override
	public void habilitarUsuario(Long id) {
		
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new IllegalStateException("El usuario con ID [ "+id+" ] no existe"));
		
		usuario.setEnabled(true);
		
		usuarioRepository.save(usuario);
	}

	@Override
	public Optional<Direccion> getDireccion(Long idUser, Long idDireccion) {
		
		Optional<Direccion> optional = direccionRepository.getDireccion(idUser, idDireccion);
		
		if(optional.isEmpty()) {
			new IllegalStateException("No existe la direccion para el usuario con id [ "+idUser+" ].");
		}
		
		return optional;
	}

	@Override
	public List<Direccion> getAllDirecciones(Long idUser) {
		
		boolean existe = usuarioRepository.existsById(idUser);
		
		if(!existe) {
			new IllegalStateException("El usuario con ID [ "+idUser+" ] no existe");
		}
		
		return direccionRepository.findByUsuarioId(idUser);
	}
	
	@Override
	public void addDireccionUser(Long userId, Direccion direccion) throws Exception {
		Usuario user = usuarioRepository.findById(userId)
				.orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));
		
		direccion.setUsuario(user);
		
		direccionRepository.save(direccion);
	}

	@Override
	public Optional<Role> findRolByIdUsuario(Long idUsuario) {
		Optional<Role> optional = usuarioRepository.findRolByIdUsuario(idUsuario);
		
		return optional.isEmpty() ? Optional.empty() : Optional.of(optional.get());
	}

	@Override
	public Optional<Usuario> findByUserName(String username) {
		Optional<Usuario> optional = usuarioRepository.findByUsername(username);
		
		return optional.isEmpty() ? Optional.empty() : Optional.of(optional.get());
	}

	@Override
	public boolean existsByEmail(String email) {
		return usuarioRepository.existsByEmail(email);
	}

	@Transactional
	@Override
	public void deleteDireccion(Long idDireccion) {
		
		boolean existe = direccionRepository.existsById(idDireccion);
		
		if(!existe) {
			new IllegalStateException("La direccion con ID [ "+idDireccion+" ] no existe");
		}
		
		Optional<Direccion> optional = direccionRepository.findById(idDireccion);
		
		if(pedidoRepository.existePedidoConDireccion(idDireccion)) {
			pedidoRepository.limpiarDireccionDePedidos(idDireccion);
		}
		
		direccionRepository.delete(optional.get());
	}

	@Override
	public void updatePasswd(Long id, String newPasswd) {
		
		Usuario user = usuarioRepository.findById(id)
				.orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));
		
		String latestPasswd = user.getPassword();
		
        String newEncodedPassword = passwordEncoder.encode(newPasswd);
		
		if(latestPasswd.equalsIgnoreCase(newEncodedPassword)) {
			throw new IllegalStateException("La contraseña nueva no puede ser la misma que la antigua.");
		}
		
		user.setPassword(newEncodedPassword);
		user.setLastPasswordResetDate(new Date());
		
		usuarioRepository.save(user);
	}

}
