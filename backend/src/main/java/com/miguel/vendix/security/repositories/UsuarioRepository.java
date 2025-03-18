package com.miguel.vendix.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguel.vendix.security.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByEmailAndPassword(String email, String password);
	
	Boolean existsByEmail(String email);
	
	Optional<Usuario> findByUserName(String userName);
}
