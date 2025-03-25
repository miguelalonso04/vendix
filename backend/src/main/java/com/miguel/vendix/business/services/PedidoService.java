package com.miguel.vendix.business.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.miguel.vendix.business.model.Pedido;

public interface PedidoService {
	
	/**
	 * Si la id no es null lanza IllegalStateException
	 * 
	 */
	Long create(Pedido pedido);
	
	Optional<Pedido> read(Long id);
	
	/**
	 * Si la id es null o no existe lanza IllegalStateException
	 * 
	 */
	void update(Pedido pedido);

	/**
	 * Si la id es null o no existe lanza IllegalStateException
	 * 
	 */
	void delete(Long id);
	
	List<Pedido> getAll();
	
	void deleteAll();
	
	List<Pedido> getByEstadoPedido(String estado);
	
	List<Pedido> getBetweenFechas(Date desde, Date hasta);
	
	void confirmarPedido(Long id);
	
	void cancelarPedido(Long id);
}
