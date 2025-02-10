package com.miguel.vendix.integration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguel.vendix.business.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByEmailAndPassword(String email, String password);
	
	Boolean existsByEmail(String email);

	Boolean existByUserName(String userName);
}
