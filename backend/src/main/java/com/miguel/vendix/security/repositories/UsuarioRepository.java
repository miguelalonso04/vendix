package com.miguel.vendix.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miguel.vendix.security.model.Role;
import com.miguel.vendix.security.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByEmailAndPassword(String email, String password);
	
	Boolean existsByEmail(String email);
	
	@Query("SELECT u.roles FROM Usuario u WHERE u.id = :idUsuario")
	Optional<Role> findRolByIdUsuario(Long idUsuario);

	Optional<Usuario> findByUsername(String username);
}
