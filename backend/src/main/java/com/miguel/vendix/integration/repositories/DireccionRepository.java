package com.miguel.vendix.integration.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.miguel.vendix.business.model.Direccion;

public interface DireccionRepository extends JpaRepository<Direccion, Long>{

	@Query("SELECT d FROM Direccion d WHERE d.usuario.id = :idUsuario")
    List<Direccion> findByUsuarioId(@Param("idUsuario") Long idUsuario);
	
	@Query("SELECT d FROM Direccion d WHERE d.usuario.id = :idUser AND d.id = :idDireccion")
	Optional<Direccion> getDireccion(@Param("idUser") Long idUser, @Param("idDireccion") Long idDireccion);
}
