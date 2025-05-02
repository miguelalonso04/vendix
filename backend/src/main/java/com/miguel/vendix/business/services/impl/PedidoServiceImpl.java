package com.miguel.vendix.business.services.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguel.vendix.business.model.CestaProductos;
import com.miguel.vendix.business.model.EstadoPedido;
import com.miguel.vendix.business.model.Pedido;
import com.miguel.vendix.business.model.Producto;
import com.miguel.vendix.business.model.dtos.PedidoDTO;
import com.miguel.vendix.business.model.dtos.ProductoDTO;
import com.miguel.vendix.business.services.PedidoService;
import com.miguel.vendix.integration.repositories.CestaProductosRepository;
import com.miguel.vendix.integration.repositories.PedidoRepository;
import com.miguel.vendix.integration.repositories.ProductoRepository;
import com.miguel.vendix.security.model.Usuario;
import com.miguel.vendix.security.repositories.UsuarioRepository;

@Service
public class PedidoServiceImpl implements PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private CestaProductosRepository cestaRepository;

	@Override
	public Long create(PedidoDTO pedidoDTO, Long idUsuario) {
		
		Pedido pedido = new Pedido();
		CestaProductos cesta = cestaRepository.findById(idUsuario)
								.orElseThrow(()->new IllegalStateException("NO SE HA ENCONTRADO LA CESTA CON ID "+idUsuario));
		Map<Producto, Integer> productos = cesta.getProductos();
		Double total = 0.0;
		
		Usuario usuario = usuarioRepository.findById(idUsuario)
				.orElseThrow(() -> new IllegalArgumentException("Usuario con id ["+idUsuario+"] no encontrado"));
		
		for (ProductoDTO productoDTO : pedidoDTO.getProductos()) {
	        System.out.println(productoDTO);
	        
	        Producto producto = productoRepository.findById(productoDTO.getId())
	                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + productoDTO.getNombre()));

	        total += productoDTO.getPrecio() * productoDTO.getCantidad();
	        
	        productos.put(producto, productoDTO.getCantidad());
	    }
		
		cesta.setProductos(productos);
		pedidoDTO.setPrecioTotalPedido(total);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime now = LocalDateTime.now();
		now.format(formatter);
		Date fechaPedido = Timestamp.valueOf(now);
		
		pedido.setCestaProductos(cesta);
		pedido.setDireccion(pedidoDTO.getDireccion());
		pedido.setUsuario(usuario);
		pedido.setPrecioTotal(total);
		pedido.setEstado(EstadoPedido.PENDIENTE);   
		pedido.setFechaPedido(fechaPedido);
		
		System.out.println(fechaPedido);
		
		Pedido pedidoCreado = pedidoRepository.save(pedido);
		
		return pedidoCreado.getId();
	}

	@Override
	public Optional<PedidoDTO> read(Long id) {
	    Optional<Pedido> optionalPedido = pedidoRepository.findById(id);

	    return optionalPedido.map(this::convertirPedidoToDTO);
	}

	@Override
	public void update(Pedido pedido) {
		
		Long id = pedido.getId();
		
		boolean existe = pedidoRepository.existsById(id);
		
		if(!existe) {
			throw new IllegalStateException("El pedido con ID [ "+id+" ] no existe");
		}
		
		pedidoRepository.save(pedido);
	}

	@Override
	public void delete(Long id) {
				
		boolean existe = pedidoRepository.existsById(id);
		
		if(!existe) {
			throw new IllegalStateException("El pedido con ID [ "+id+" ] no existe");
		}
		
		Optional<Pedido> optional = pedidoRepository.findById(id);
		
		pedidoRepository.delete(optional.get());
	}

	@Override
	public List<Pedido> getAll() {
		return pedidoRepository.findAll();
	}

	@Override
	public void deleteAll() {
		pedidoRepository.deleteAll();
	}

	@Override
	public List<Pedido> getByEstadoPedido(String estado) {
		
		return pedidoRepository.findAll().stream()
								  .filter(e -> e.getEstado()
										  		.equals(EstadoPedido.valueOf(estado)))
								  .toList();
	}

	@Override
	public List<Pedido> getBetweenFechas(Date desde, Date hasta) {
		return pedidoRepository.findByFechaPedidoBetweenOrderByFechaPedidoDesc(desde, hasta);
	}

	@Override
	public void confirmarPedido(Long id) {
		boolean existe = pedidoRepository.existsById(id);
		
		if(!existe) {
			throw new IllegalStateException("El pedido con ID [ "+id+" ] no existe");
		}
		
		Optional<Pedido> optional = pedidoRepository.findById(id);
		
		Date fechaActual = new Date();
		
		optional.get().setFechaPedido(fechaActual);
		
		optional.get().setEstado(EstadoPedido.ENVIADO);
		
	}

	@Override
	public void cancelarPedido(Long id) {
		boolean existe = pedidoRepository.existsById(id);
		
		if(!existe) {
			throw new IllegalStateException("El pedido con ID [ "+id+" ] no existe");
		}
		
		Optional<Pedido> optional = pedidoRepository.findById(id);
		
		optional.get().setEstado(EstadoPedido.CANCELADO);
		
	}

	@Override
	public Optional<Usuario> getUsuarioByPedido(Long id) {
		
		boolean existe = pedidoRepository.existsById(id);
		
		if(!existe) {
			throw new IllegalStateException("El pedido con ID [ "+id+" ] no existe");
		}
		
		Optional<Usuario> optional = pedidoRepository.findUsuarioByid(id);
		
		return optional.isEmpty() ? Optional.empty() : Optional.of(optional.get());
	}
	
	private PedidoDTO convertirPedidoToDTO(Pedido pedido) {
		PedidoDTO pedidoDTO = new PedidoDTO();
		ArrayList<ProductoDTO> lista = new ArrayList<>();
		
		pedidoDTO.setDireccion(pedido.getDireccion());
		pedidoDTO.setNombreUsuario(pedido.getUsuario().getUsername());
		pedidoDTO.setPrecioTotalPedido(pedido.getPrecioTotal());
		pedidoDTO.setFechaPedido(pedido.getFechaPedido());
		pedidoDTO.setId(pedido.getId());
		pedidoDTO.setEstado(pedido.getEstado());

		for (Producto p :pedido.getCestaProductos().getProductos().keySet() ) {
			
			ProductoDTO productoDTO = new ProductoDTO();
			productoDTO.setId(p.getId());
			productoDTO.setNombre(p.getNombre());
			productoDTO.setPrecio(p.getPrecio());
			productoDTO.setCantidad(pedido.getCestaProductos().getProductos().get(p));
			
			lista.add(productoDTO);
		}
		pedidoDTO.setProductos(lista);
		
		return pedidoDTO;
		
	}

}
