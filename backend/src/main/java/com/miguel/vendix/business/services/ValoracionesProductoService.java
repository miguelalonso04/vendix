package com.miguel.vendix.business.services;

import java.util.List;
import java.util.Optional;

import com.miguel.vendix.business.model.ValoracionesProducto;

public interface ValoracionesProductoService {

	Long create(ValoracionesProducto valoracion, Long idProducto, Long idUsuario);
	
	Optional<ValoracionesProducto> read(Long id);
	
	void update(ValoracionesProducto valoracion);
	
	void delete(Long id);
	
	List<ValoracionesProducto> getAll();
	
	List<ValoracionesProducto> getAllByProducto(Long idProducto);
	
	List<ValoracionesProducto> getAllByUsername(String username);
	
	Integer numeroValoracionesXProducto(Long idProducto);
	
	Double mediaValoracionesXProducto(Long idProducto);
}
