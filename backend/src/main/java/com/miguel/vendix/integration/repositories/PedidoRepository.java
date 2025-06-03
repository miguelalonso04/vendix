package com.miguel.vendix.integration.repositories;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.miguel.vendix.business.model.Pedido;
import com.miguel.vendix.security.model.Usuario;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
	List<Pedido> findByFechaPedidoBetweenOrderByFechaPedidoDesc(Date desde, Date hasta);
	
	@Query("SELECT p.usuario FROM Pedido p WHERE p.id = :id")
	Optional<Usuario> findUsuarioByid(@Param("id") Long id);
	
	@Query("SELECT p FROM Pedido p WHERE p.usuario.id = :usuarioId")
    List<Pedido> findPedidosByUsuarioId(@Param("usuarioId") Long usuarioId);
	
	@Modifying
	@Query("UPDATE Pedido p SET p.direccion = NULL WHERE p.direccion.id = :direccionId")
	void limpiarDireccionDePedidos(@Param("direccionId") Long direccionId);
	
	@Query("SELECT COUNT(p) > 0 FROM Pedido p WHERE p.direccion.id = :direccionId")
    boolean existePedidoConDireccion(@Param("direccionId") Long direccionId);
	
	@Query("SELECT p FROM Pedido p WHERE p.estado = 'PENDIENTE' AND p.fechaPedido <= :fechaLimite")
	List<Pedido> findPedidosPendientesAntiguos(@Param("fechaLimite") LocalDateTime fechaLimite);

	@Query("SELECT p FROM Pedido p WHERE p.usuario.id = :idUsuario AND p.fechaPedido BETWEEN :desde AND :hasta ORDER BY p.fechaPedido DESC")
	List<Pedido> findByUsuarioAndFechaPedidoBetweenOrderByFechaPedidoDesc(
	    @Param("idUsuario") Long idUsuario,
	    @Param("desde") Date desde,
	    @Param("hasta") Date hasta
	);
}
