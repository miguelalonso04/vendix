package com.miguel.vendix.business.services;

import java.util.List;
import java.util.Optional;

import com.miguel.vendix.business.model.Producto;

public interface ProductoService {

	/**
	 * Si la id no es null lanza IllegalStateException
	 * 
	 */
	Long create(Producto producto);
	
	Optional<Producto> read(Long id);
	
	/**
	 * Si la id es null o no existe lanza IllegalStateException
	 * 
	 */
	void update(Producto producto);
	
	/**
	 * Si la id es null o no existe lanza IllegalStateException
	 * 
	 */
	void delete(Long id);
	
	List<Producto> getAll();
	
	/**
	 * Incluye los extremos
	 * 
	 */
	List<Producto> getBetweenPriceRange(double min, double max);
	
	
	int getNumeroTotalProductos();

	/**
	 * Incrementa el precio de todos los productos que se aportan 
	 * según el porcentaje que se indica.
	 * 
	 * Ejemplo: si porcentaje = 20.0 se incrementa el precio un 20%
	 * 
	 */
	void incrementarPrecio(List<Producto> productos, double porcentaje);
	
	/**
	 * Incrementa el precio de todos los productos que se aportan
	 * según el porcentaje que se indica.
	 * 
	 * (el argumento ids representa los ids de los productos) 
	 * 
	 * Ejemplo: si porcentaje = 20.0 se incrementa el precio un 20%
	 * 
	 */
	void incrementarPrecio(double porcentaje, Long... ids);
}
