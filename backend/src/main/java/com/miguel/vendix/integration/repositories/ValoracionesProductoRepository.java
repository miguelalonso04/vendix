package com.miguel.vendix.integration.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.miguel.vendix.business.model.ValoracionesProducto;

public interface ValoracionesProductoRepository extends JpaRepository<ValoracionesProducto, Long>{

	@Query("SELECT v FROM ValoracionesProducto v WHERE v.producto.id = :idProducto")
    List<ValoracionesProducto> findByProductoId(@Param("idProducto") Long idProducto);
	
	@Query("SELECT v FROM ValoracionesProducto v WHERE v.usuario.username = :username")
	List<ValoracionesProducto> findByUsername(@Param("username") String username);
	
	@Query("SELECT COUNT(v) FROM ValoracionesProducto v WHERE v.producto.id = :idProducto")
    Integer numeroValoracionesXProducto(@Param("idProducto") Long idProducto);
	
	@Query("SELECT ROUND(AVG(v.valoracion), 1) FROM ValoracionesProducto v WHERE v.producto.id = :idProducto")
	Double mediaValoracionesPorProducto(@Param("idProducto") Long idProducto);
}
