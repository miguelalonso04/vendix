package com.miguel.vendix.integration.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguel.vendix.business.model.Pedido;
import com.miguel.vendix.security.model.Usuario;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
	List<Pedido> findByFechaPedidoBetweenOrderByFechaPedidoDesc(Date desde, Date hasta);
	
	Optional<Usuario> findUsuarioByid(Long id);
	
	@Query("SELECT p FROM Pedido p WHERE p.usuario.id = :usuarioId")
    List<Pedido> findPedidosByUsuarioId(@Param("usuarioId") Long usuarioId);

}
