package com.miguel.vendix.business.services;

import java.util.List;
import java.util.Optional;

import com.miguel.vendix.business.model.Producto;

public interface ProductoService {

	/**
	 * Si la id no es null lanza IllegalStateException
	 * 
	 */
	Long create(Producto producto, Long idUsuario);
	
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

	List<Producto> getlAllByCategoria(Long idCategoria);
	
	List<Producto> getAllByNombre(String nombreProducto);
	
	void actualizarRutaImagen(Long productoId, String rutaImagen);
	
	String getRutaImagen(Long productoId);
	
	List<Producto> getAllByUsuario(Long idUsuario);
	
}
