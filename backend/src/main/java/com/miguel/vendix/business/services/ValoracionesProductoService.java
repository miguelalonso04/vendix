package com.miguel.vendix.business.services;

import java.util.List;
import java.util.Optional;

import com.miguel.vendix.business.model.ValoracionesProducto;

public interface ValoracionesProductoService {

	Long create(ValoracionesProducto valoracion);
	
	Optional<ValoracionesProducto> read(Long idProducto);
	
	void update(ValoracionesProducto valoracion);
	
	void delete(Long id);
	
	List<ValoracionesProducto> getAll();
	
	
}
