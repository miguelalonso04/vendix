package com.miguel.vendix.business.services;

import java.util.List;
import java.util.Optional;

import com.miguel.vendix.business.model.Direccion;
import com.miguel.vendix.security.model.Role;
import com.miguel.vendix.security.model.Usuario;

public interface UsuarioService {

	/**
	 * Metodo para logear al usuario
	 * 
	 * @param email
	 * @param password
	 * @return devuelve un optional de usuario, ya que puede que el usuario sea null o no exista.
	 */
	Optional<Usuario> login(String email, String password);
	
	/**
	 * Metodo para registrar al usuario
	 * 
	 * @param email
	 * @param password
	 * @return devuelve el id del usuario registrado
	 * @throws Exception 
	 */
	Long register(Usuario user);
	
	Optional<Usuario> read(Long id);
	
	List<Usuario> getAll();
	
	/**
	 * Si la id es null o no existe lanza IllegalStateException
	 * 
	 */
	void delete(Long id);
	
	Long addDireccionUser(Long userId, Direccion direccion) throws Exception;
	
	Optional<Role> findRolByIdUsuario(Long idUsuario);
	
	Optional<Usuario> findByUserName(String username);
	
	boolean existsByEmail(String email);
}
