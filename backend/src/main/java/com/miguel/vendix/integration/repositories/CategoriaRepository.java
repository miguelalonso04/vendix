package com.miguel.vendix.integration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguel.vendix.business.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

	boolean existsByNombre(String nombre);
	
}
