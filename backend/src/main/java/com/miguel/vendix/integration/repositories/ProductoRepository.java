package com.miguel.vendix.integration.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.miguel.vendix.business.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

	List<Producto> findByPrecioBetweenOrderByPrecioDesc(double min, double max);
	
	List<Producto> findByFechaAltaBetweenOrderByFechaAltaDesc(Date desde, Date hasta);
	
	@Modifying
	@Query("UPDATE Producto p SET p.precio = p.precio + (p.precio * :porcentaje) / 100 WHERE p IN :productos")
	void incrementarPrecio(List<Producto> productos, double porcentaje);
	
	@Modifying
	@Query("UPDATE Producto p SET p.precio = p.precio + (p.precio * :porcentaje) / 100 WHERE p.id IN :ids")
	void incrementarPrecio(double porcentaje, Long[] ids);
	
}
