package com.miguel.vendix.business.services;

import java.util.List;
import java.util.Optional;

import com.miguel.vendix.business.model.Categoria;

public interface CategoriaService {
	
	/**
	 * Si la id no es null lanza IllegalStateException
	 * 
	 */
	Long create(Categoria categoria);
	
	Optional<Categoria> read(Long id);
	
	/**
	 * Si la id es null o no existe lanza IllegalStateException
	 * 
	 */
	void update(Categoria categoria);
	
	/**
	 * Si la id es null o no existe lanza IllegalStateException
	 * 
	 */
	void delete(Long id);
	
	List<Categoria> getAll();

}
