package com.miguel.vendix.business.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.miguel.vendix.business.model.CestaProductos;
import com.miguel.vendix.business.model.Producto;
import com.miguel.vendix.business.services.CestaProductoService;
import com.miguel.vendix.integration.repositories.CestaProductosRepository;


@Service
public class CestaProductoServiceImpl implements CestaProductoService {

	private CestaProductosRepository cestaRepository;
	
		
	public CestaProductoServiceImpl(CestaProductosRepository cestaRepository) {
		this.cestaRepository = cestaRepository;
	}

	@Override
	public Optional<CestaProductos> read(Long id) {
		
		Optional <CestaProductos> optional = cestaRepository.findById(id);
		
		return optional.isEmpty() ? Optional.empty() : Optional.of(optional.get());
	}

	@Override
	public void añadirProductoACesta(Producto producto, Long idCesta) {
		
		//Comprobamos que existe la cesta o la creamos
		 CestaProductos cesta = cestaRepository.findById(idCesta)
		            .orElseGet(() -> {
		                CestaProductos nuevaCesta = new CestaProductos();
		                nuevaCesta.setId(idCesta);
		                nuevaCesta.setProductos(new ArrayList<Producto>());
		                nuevaCesta.setTotal(0);
		                return cestaRepository.save(nuevaCesta);
		            });

		List<Producto> lProducto = cesta.getProductos();
		
		lProducto.add(producto);
		
		cestaRepository.save(cesta);
	}

	@Override
	public void vaciarCesta(Long idCesta) {
		
		CestaProductos cesta = cestaRepository.findById(idCesta)
				.orElseThrow(() -> new IllegalStateException("NO SE HA ENCONTRADO LA CESTA DEL USUARIO "+idCesta));	
		
		cesta.getProductos().clear();
		
		cesta.setTotal(0);
		
		cestaRepository.save(cesta);
	}

	@Override
	public void eliminarUnProductoEnCesta(Long idProducto, Long idCesta) {
		
		CestaProductos cesta = cestaRepository.findById(idCesta)
				.orElseThrow(() -> new IllegalStateException("NO SE HA ENCONTRADO LA CESTA DEL USUARIO "+idCesta));	
		
		if(cesta.getProductos().isEmpty()) {
			throw new IllegalStateException("NO HAY PRODUCTOS EN LA CESTA");
		}
		
		cesta.getProductos()
				.removeIf(p -> p.getId().equals(idProducto));
		
		cestaRepository.save(cesta);
	}

	@Override
	public List<Producto> getAllProductos(Long idCesta) {
		
		CestaProductos cesta = cestaRepository.findById(idCesta)
				.orElseThrow(() -> new IllegalStateException("NO SE HA ENCONTRADO LA CESTA DEL USUARIO "+idCesta));	
		
		if(cesta.getProductos().isEmpty()) {
			throw new IllegalStateException("NO HAY PRODUCTOS EN LA CESTA");
		}
		
		return cesta.getProductos();
	}

	@Override
	public Long getCantidadXProducto(Long idProducto, Long idCesta) {
		
		CestaProductos cesta = cestaRepository.findById(idCesta)
	            .orElseThrow(() -> new IllegalStateException("NO SE HA ENCONTRADO LA CESTA DEL USUARIO " + idCesta));    
	    
	    return cesta.getProductos().stream()
	            .filter(p -> p.getId().equals(idProducto))
	            .count();
	}

	@Override
	public Double getPrecioXProducto(Long idProducto, Long idCesta) {

		CestaProductos cesta = cestaRepository.findById(idCesta)
	            .orElseThrow(() -> new IllegalStateException("NO SE HA ENCONTRADO LA CESTA DEL USUARIO " + idCesta));    

		Optional <Double> precio = cesta.getProductos().stream()
				.filter(p -> p.getId().equals(idProducto))
				.map(Producto::getPrecio)
				.findFirst();
				
		return precio.get();
	}

	@Override
	public Double calcularTotalCesta(Long idCesta) {
		
		CestaProductos cesta = cestaRepository.findById(idCesta)
	            .orElseThrow(() -> new IllegalStateException("NO SE HA ENCONTRADO LA CESTA DEL USUARIO " + idCesta));    

	    return cesta.getProductos().stream()
	            .mapToDouble(Producto::getPrecio)
	            .sum();
	}

	

}
