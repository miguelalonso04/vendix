package com.miguel.vendix.business.services;

import java.util.Map;
import java.util.Optional;

import com.miguel.vendix.business.model.CestaProductos;
import com.miguel.vendix.business.model.Producto;

public interface CestaProductoService {
	
	Long create(CestaProductos cesta);

	/**
	 * Solo podra haber 1 cesta por usuario
	 * 
	 * @param idUsuario tiene que coincidir con el idCesta
	 * 
	 */
	Optional<CestaProductos> read(Long id);
	
	/**
	 * Si la id es null o no existe lanza IllegalStateException
	 * 
	 */
	void añadirProductoACesta(Producto producto, Long idCesta);
	
	/**
	 * Si la id es null o no existe lanza IllegalStateException
	 * 
	 */
	void vaciarCesta(Long id);
	
	/**
	 * Si la id es null o no existe lanza IllegalStateException
	 * 
	 */
	void eliminarUnProductoEnCesta(Long idProducto, Long idCesta);
	
	Map<Producto,Integer> getAllProductos(Long idCesta);
	
	/**
	 * Si la id es null o no existe lanza IllegalStateException
	 * 
	 */
	Integer getCantidadXProducto(Long idProducto, Long idCesta);
	
	/**
	 * Si la id es null o no existe lanza IllegalStateException
	 * 
	 */
	Double getPrecioXProducto(Long idProducto, Long idCesta);
	
	Double calcularTotalCesta(Long idCesta);                                                                                                                                                           
	
	void sumarCantidadXProducto(Long idProducto, Long idCesta);
	
	void restarCanitdadXProducto(Long idProducto, Long idCesta);
	
}
