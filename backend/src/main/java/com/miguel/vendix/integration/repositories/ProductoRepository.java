package com.miguel.vendix.integration.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miguel.vendix.business.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

	List<Producto> findByPrecioBetweenOrderByPrecioDesc(double min, double max);
	
	@Query("SELECT p FROM Producto p WHERE p.categoria.id = :idCategoria")
    List<Producto> findByCategoriaId(Long idCategoria);
	
	Optional<Producto> findByNombre(String nombre);
}
