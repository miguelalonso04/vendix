package com.miguel.vendix.business.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.miguel.vendix.business.model.Producto;
import com.miguel.vendix.business.services.ProductoService;
import com.miguel.vendix.integration.repositories.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductoServiceImpl implements ProductoService {

	private final ProductoRepository productoRepository;
	
	public ProductoServiceImpl(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}
	
	@Override
	@Transactional 
	//Transactional: si ocurre una exception las operaciones de la base de datos se revertiran
	public Long create(Producto producto) {
		
		if(producto.getId() != null) {
			throw new IllegalStateException("Para crear un producto el id ha de ser null");
		}
		
		Producto productoCreado = productoRepository.save(producto);
		
		return productoCreado.getId();
	}

	@Override
	public Optional<Producto> read(Long id) {
		
		Optional<Producto> optional = productoRepository.findById(id);
		
		return optional.isEmpty() ? Optional.empty() : Optional.of(optional.get());
	}

	@Override
	@Transactional
	public void update(Producto producto) {
		
		Long id = producto.getId();
		
		boolean existe = productoRepository.existsById(id);
		
		if(!existe) {
			throw new IllegalStateException("El producto con ID ["+ id +"] no existe ");
		}
		
		productoRepository.save(producto);
		
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		boolean existe = productoRepository.existsById(id);
		
		if(!existe) {
			throw new IllegalStateException("El producto con ID [" + id + "] no existe.");	
		}
    
		Optional<Producto> optional = productoRepository.findById(id);
		
		optional.get().setDisponible(false); 
		
		productoRepository.delete(optional.get());
	}

	@Override
	public List<Producto> getAll() {
		return productoRepository.findAll();
	}

	@Override
	public List<Producto> getBetweenPriceRange(double min, double max) {
		return productoRepository.findByPrecioBetweenOrderByPrecioDesc(min, max);
	}

	@Override
	public int getNumeroTotalProductos() {
		return (int) productoRepository.count();
	}

	@Override
	public void incrementarPrecio(List<Producto> productos, double porcentaje) {
		productoRepository.incrementarPrecio(productos, porcentaje);
	}

	@Override
	public void incrementarPrecio(double porcentaje, Long... ids) {
		productoRepository.incrementarPrecio(porcentaje, ids);
	}

}
