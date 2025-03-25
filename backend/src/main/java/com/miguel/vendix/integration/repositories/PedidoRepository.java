package com.miguel.vendix.integration.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguel.vendix.business.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
	List<Pedido> findByFechaPedidoBetweenOrderByFechaPedidoDesc(Date desde, Date hasta);

}
