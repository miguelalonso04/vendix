package com.miguel.vendix.business.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguel.vendix.business.model.EstadoPedido;
import com.miguel.vendix.business.model.Pedido;
import com.miguel.vendix.business.services.PedidoService;
import com.miguel.vendix.integration.repositories.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;

	@Override
	public Long create(Pedido pedido) {
		
		if(pedido.getId() != null) {
			throw new IllegalStateException("Para crear un pedido el id tiene que ser null");
		}
		
		pedido.setEstado(EstadoPedido.PENDIENTE);
		
		Pedido pedidoCreado = pedidoRepository.save(pedido);
		
		return pedidoCreado.getId();
	}

	@Override
	public Optional<Pedido> read(Long id) {
		
		Optional<Pedido> optional = pedidoRepository.findById(id);
		
		return optional.isEmpty() ? Optional.empty() : Optional.of(optional.get());
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

}
