package com.miguel.vendix.business.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.miguel.vendix.business.model.CestaProductos;
import com.miguel.vendix.business.model.Producto;
import com.miguel.vendix.business.model.dtos.CestaDTO;
import com.miguel.vendix.business.model.dtos.ProductoDTO;
import com.miguel.vendix.business.services.CestaProductoService;
import com.miguel.vendix.integration.repositories.CestaProductosRepository;
import com.miguel.vendix.integration.repositories.ProductoRepository;

import jakarta.transaction.Transactional;


@Service
public class CestaProductoServiceImpl implements CestaProductoService {

	private CestaProductosRepository cestaRepository;
	
	private ProductoRepository productoRepository;
	
		
	public CestaProductoServiceImpl(CestaProductosRepository cestaRepository, ProductoRepository productoRepository) {
		this.cestaRepository = cestaRepository;
		this.productoRepository = productoRepository;
	}

	@Transactional
	@Override
	public Long create(CestaProductos cesta) {
		
	    return cestaRepository.save(cesta).getId();
	}
	
	@Override
	public Optional<CestaDTO> read(Long id) {
		
		Optional <CestaProductos> optional = cestaRepository.findById(id);
		
		CestaDTO cestaDTO = convertirACestaDTO(optional.get());
		
		return optional.isEmpty() ? Optional.empty() : Optional.of(cestaDTO);
	}

	@Override
	public void añadirProductoACesta(Producto p, Long idCesta) {
	
		 CestaProductos cesta = cestaRepository.findById(idCesta)
				 .orElseThrow(() -> new IllegalStateException("No se ha encontrado la cesta con id "+ idCesta));
		 
		Map<Producto,Integer> productos = cesta.getProductos();
		
		Double total = cesta.getTotal();
		
		if(p.getStock() > 0) {
			cesta.setTotal(total + p.getPrecio());
			
			if(productos.containsKey(p)) {
				productos.replace(p , productos.get(p) + 1);
				
			}else {
				productos.put(p, 1);
			}
			
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
		
		System.out.println(producto.getPrecio() * cesta.getProductos().get(producto));
		
		cesta.setTotal(cesta.getTotal() - (producto.getPrecio() * cesta.getProductos().get(producto)));
		
		
		cestaRepository.save(cesta);
	}

	@Override
	public List<ProductoDTO> getAllProductos(Long idCesta) {
		
		CestaProductos cesta = cestaRepository.findById(idCesta)
				.orElseThrow(() -> new IllegalStateException("NO SE HA ENCONTRADO LA CESTA DEL USUARIO "+idCesta));	
		
		if(cesta.getProductos().isEmpty()) {
			throw new IllegalStateException("NO HAY PRODUCTOS EN LA CESTA");
		}
		
		CestaDTO cestaDTO = convertirACestaDTO(cesta);
		
		return cestaDTO.getProductos();
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

	    return cesta.getProductos().entrySet().stream()
	    		.mapToDouble(entry -> entry.getKey().getPrecio() * entry.getValue())
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
	

	//CONVERTIR DTO
	
	public CestaDTO convertirACestaDTO(CestaProductos cesta) {
	    List<ProductoDTO> lista = new ArrayList<>();

	    for (Producto p : cesta.getProductos().keySet()) {
	    	Integer cantidad = this.getCantidadXProducto(p.getId(), cesta.getId());
	        ProductoDTO item = new ProductoDTO(p.getId(),p.getNombre(),p.getPrecio(), cantidad);
	        lista.add(item);
	    }

	    return new CestaDTO(cesta.getId(), cesta.getTotal(), lista);
	}
	
}
