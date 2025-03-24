package com.miguel.vendix.business.services.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.miguel.vendix.business.model.CestaProductos;
import com.miguel.vendix.business.model.Producto;
import com.miguel.vendix.business.services.CestaProductoService;
import com.miguel.vendix.integration.repositories.CestaProductosRepository;
import com.miguel.vendix.integration.repositories.ProductoRepository;


@Service
public class CestaProductoServiceImpl implements CestaProductoService {

	private CestaProductosRepository cestaRepository;
	
	private ProductoRepository productoRepository;
	
		
	public CestaProductoServiceImpl(CestaProductosRepository cestaRepository, ProductoRepository productoRepository) {
		this.cestaRepository = cestaRepository;
		this.productoRepository = productoRepository;
	}

	@Override
	public Long create(CestaProductos cesta) {
		CestaProductos cestaCreada = cestaRepository.save(cesta);
		
		return cestaCreada.getId();
	}
	
	@Override
	public Optional<CestaProductos> read(Long id) {
		
		Optional <CestaProductos> optional = cestaRepository.findById(id);
		
		return optional.isEmpty() ? Optional.empty() : Optional.of(optional.get());
	}

	@Override
	public void añadirProductoACesta(Producto p, Long idCesta) {
	
		 CestaProductos cesta = cestaRepository.findById(idCesta)
				 .orElseThrow(() -> new IllegalStateException("No se ha encontrado la cesta con id "+ idCesta));
		 
		Map<Producto,Integer> productos = cesta.getProductos();
		
		Double total = cesta.getTotal();
		
		if(p.getStock() > 1) {
			cesta.setTotal(total + p.getPrecio());
			productos.put(p, 1);
		}else {
			throw new IllegalStateException("NO HAY SUFICIENTE STOCK DE "+p.getNombre());
			
		}
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
		
		Producto producto = productoRepository.findById(idProducto)
				.orElseThrow(() -> new IllegalStateException("NO SE HA ENCONTRADO EL PRODUCTO CON ID "+idProducto) );
		
		if(cesta.getProductos().containsKey(producto)) {
			cesta.getProductos().remove(producto);
		}else {
			new IllegalStateException("El producto "+producto.getNombre()+" no se encuentra en la cesta");
		}
		
		cesta.setTotal(cesta.getTotal() - producto.getPrecio());
		
		cestaRepository.save(cesta);
	}

	@Override
	public Map<Producto,Integer> getAllProductos(Long idCesta) {
		
		CestaProductos cesta = cestaRepository.findById(idCesta)
				.orElseThrow(() -> new IllegalStateException("NO SE HA ENCONTRADO LA CESTA DEL USUARIO "+idCesta));	
		
		if(cesta.getProductos().isEmpty()) {
			throw new IllegalStateException("NO HAY PRODUCTOS EN LA CESTA");
		}
		
		return cesta.getProductos();
	}

	@Override
	public Integer getCantidadXProducto(Long idProducto, Long idCesta) {
		
		CestaProductos cesta = cestaRepository.findById(idCesta)
	            .orElseThrow(() -> new IllegalStateException("NO SE HA ENCONTRADO LA CESTA DEL USUARIO " + idCesta));    
	    
		Producto producto = productoRepository.findById(idProducto)
				.orElseThrow(() -> new IllegalStateException("NO SE HA ENCONTRADO EL PRODUCTO CON ID "+idProducto) );
		
		Map<Producto, Integer> productos = cesta.getProductos();
		
		if(!productos.containsKey(producto)){
			throw new IllegalStateException("El producto "+producto.getNombre()+" no se encuentra en la cesta");
		}
		
	    return productos.get(producto);
	}

	@Override
	public Double getPrecioXProducto(Long idProducto, Long idCesta) {

		CestaProductos cesta = cestaRepository.findById(idCesta)
	            .orElseThrow(() -> new IllegalStateException("NO SE HA ENCONTRADO LA CESTA DEL USUARIO " + idCesta));    

		Optional <Double> precio = cesta.getProductos().keySet().stream()
				.filter(p -> p.getId().equals(idProducto))
				.map(Producto::getPrecio)
				.findFirst();
				
		return precio.get();
	}

	@Override
	public Double calcularTotalCesta(Long idCesta) {
		
		CestaProductos cesta = cestaRepository.findById(idCesta)
	            .orElseThrow(() -> new IllegalStateException("NO SE HA ENCONTRADO LA CESTA DEL USUARIO " + idCesta));    

	    return cesta.getProductos().keySet().stream()
	            .mapToDouble(Producto::getPrecio)
	            .sum();
	}

	@Override
	public void sumarCantidadXProducto(Long idProducto, Long idCesta) {
		
		CestaProductos cesta = cestaRepository.findById(idCesta)
	            .orElseThrow(() -> new IllegalStateException("NO SE HA ENCONTRADO LA CESTA DEL USUARIO " + idCesta));    
	    
		Producto producto = productoRepository.findById(idProducto)
				.orElseThrow(() -> new IllegalStateException("NO SE HA ENCONTRADO EL PRODUCTO CON ID "+idProducto) );
		
		Map<Producto, Integer> productos = cesta.getProductos();
		
		if(!productos.containsKey(producto)){
			throw new IllegalStateException("El producto "+producto.getNombre()+" no se encuentra en la cesta");
		}
		
		if(producto.getStock() > 1) {
			productos.put(producto, productos.get(producto) + 1);
	    	cesta.setTotal(cesta.getTotal() + producto.getPrecio());
		}
	    	
	    cestaRepository.save(cesta);
	}

	@Override
	public void restarCanitdadXProducto(Long idProducto, Long idCesta) {
		
		CestaProductos cesta = cestaRepository.findById(idCesta)
	            .orElseThrow(() -> new IllegalStateException("NO SE HA ENCONTRADO LA CESTA DEL USUARIO " + idCesta));    
	    
		Producto producto = productoRepository.findById(idProducto)
				.orElseThrow(() -> new IllegalStateException("NO SE HA ENCONTRADO EL PRODUCTO CON ID "+idProducto) );
		
		Map<Producto, Integer> productos = cesta.getProductos();
		
		if(!productos.containsKey(producto)){
			throw new IllegalStateException("El producto "+producto.getNombre()+" no se encuentra en la cesta");
		}
			
		if(producto.getStock() > 1) {
			productos.put(producto, productos.get(producto) - 1);
	    	cesta.setTotal(cesta.getTotal() - producto.getPrecio());
		}else {
			throw new IllegalStateException("NO HAY SUFICIENTE STOCK DE "+producto.getNombre());
		}

	    cestaRepository.save(cesta);
	}
	

}
