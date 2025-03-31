package com.miguel.vendix.business.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguel.vendix.business.model.Producto;
import com.miguel.vendix.business.model.ValoracionesProducto;
import com.miguel.vendix.business.services.ValoracionesProductoService;
import com.miguel.vendix.integration.repositories.ProductoRepository;
import com.miguel.vendix.integration.repositories.ValoracionesProductoRepository;
import com.miguel.vendix.security.model.Usuario;
import com.miguel.vendix.security.repositories.UsuarioRepository;

@Service
public class ValoracionesProductoServiceImpl implements ValoracionesProductoService {

	@Autowired
	private ValoracionesProductoRepository valoracionesRepository;
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public Long create(ValoracionesProducto valoracion, Long idProducto, Long idUsuario) {
		
		Producto producto = productoRepository.findById(idProducto)
				.orElseThrow(()-> new IllegalStateException("No existe el producto para valorar con id "+idProducto));
		
		Usuario usuario = usuarioRepository.findById(idUsuario)
				.orElseThrow(()-> new IllegalStateException("No existe el usuario con id "+idUsuario));
		
		valoracion.setProducto(producto);
		valoracion.setUsuario(usuario);
		
		ValoracionesProducto valoracionCreada = valoracionesRepository.save(valoracion);
		
		return valoracionCreada.getId();
	}

	@Override
	public Optional<ValoracionesProducto> read(Long id) {
		Optional <ValoracionesProducto> optional = valoracionesRepository.findById(id);
		return optional.isEmpty() ? Optional.empty() : Optional.of(optional.get());
	}

	@Override
	public void update(ValoracionesProducto valoracion) {
		
		Long id = valoracion.getId();
		
		boolean existe = valoracionesRepository.existsById(id);
		
		if(!existe)
			throw new IllegalStateException("La valoracion con id ["+id+"] no existe.");
		
		valoracionesRepository.save(valoracion);
	}

	@Override
	public void delete(Long id) {
		
		boolean existe = valoracionesRepository.existsById(id);
		
		if(!existe)
			throw new IllegalStateException("La valoracion con id ["+id+"] no existe.");
		
		Optional <ValoracionesProducto> optional = valoracionesRepository.findById(id);
		
		valoracionesRepository.delete(optional.get());
	}

	@Override
	public List<ValoracionesProducto> getAll() {
		return valoracionesRepository.findAll();
	}

	@Override
	public List<ValoracionesProducto> getAllByProducto(Long idProducto) {
		
		boolean existe = productoRepository.existsById(idProducto);
		
		if(!existe)
			throw new IllegalStateException("El producto con id ["+idProducto+"] no existe.");
		
		return valoracionesRepository.findByProductoId(idProducto);
	}

	@Override
	public List<ValoracionesProducto> getAllByUsername(String username) {

		if(!usuarioRepository.existsByusername(username))
			throw new IllegalStateException("El usuario "+username+" no existe.");
		
		return valoracionesRepository.findByUsername(username);
	}

	@Override
	public Integer numeroValoracionesXProducto(Long idProducto) {
		boolean existe = productoRepository.existsById(idProducto);
		
		if(!existe)
			throw new IllegalStateException("El producto con id ["+idProducto+"] no existe.");
		
		return valoracionesRepository.numeroValoracionesXProducto(idProducto);
	}

	@Override
	public Double mediaValoracionesXProducto(Long idProducto) {
		boolean existe = productoRepository.existsById(idProducto);
		
		if(!existe)
			throw new IllegalStateException("El producto con id ["+idProducto+"] no existe.");
		
		return valoracionesRepository.mediaValoracionesPorProducto(idProducto);
	}

}
