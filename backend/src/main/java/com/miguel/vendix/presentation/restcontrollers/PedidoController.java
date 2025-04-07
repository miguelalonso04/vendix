package com.miguel.vendix.presentation.restcontrollers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.miguel.vendix.business.model.Pedido;
import com.miguel.vendix.business.model.dtos.PedidoDTO;
import com.miguel.vendix.business.services.PedidoService;
import com.miguel.vendix.presentation.config.PresentationException;
import com.miguel.vendix.security.model.Usuario;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;
	
	@PostMapping("/crear")
	public ResponseEntity<?> createPedido(@RequestBody PedidoDTO pedidoDTO, @RequestParam Long idUsuario, UriComponentsBuilder ucb){
		
		Long id = null;
		
		try {
			id = pedidoService.create(pedidoDTO, idUsuario);
		} catch (IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.created(ucb.path("/pedidos/{id}").build(id)).build();
	}
	
	@GetMapping("/{id}")
	public Pedido getPedido(@PathVariable Long id) {
		
		Optional<Pedido> optional = pedidoService.read(id);
		
		if(optional.isEmpty()) {
			throw new PresentationException("No existe el pedido con id " + id, HttpStatus.NOT_FOUND);
		}
		
		return optional.get();
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePedido(@RequestBody Pedido pedido, @PathVariable Long id) {
		pedido.setId(id);
		
		try {
			pedidoService.update(pedido);
		} catch(IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}	
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProducto(@PathVariable Long id){
		try {
			pedidoService.delete(id);
		} catch(IllegalStateException e) {
			throw new PresentationException("El pedido con ID [" + id + "] no existe.", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping
	public List<Pedido> getAll(){
		return pedidoService.getAll();
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAll() {		
		try {
			pedidoService.deleteAll();
		} catch(IllegalStateException e) {
			throw new PresentationException("No hay pedidos actualmente.", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/fechas")
	public List<Pedido> getBeetweenFechas(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date  desde,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date hasta){
		
		return pedidoService.getBetweenFechas(desde, hasta);
	}
	
	@PutMapping("/confirmar")
	public void confirmarPedido(@RequestParam Long id) {
		try {
			pedidoService.confirmarPedido(id);
		} catch(IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/cancelar")
	public void cancelarPedido(@RequestParam Long id) {
		
		try {
			pedidoService.cancelarPedido(id);
		} catch(IllegalStateException e) {
			throw new PresentationException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{idPedido}/usuario")
	public Usuario getUsuario(@PathVariable Long idPedido) {
		
		Optional<Usuario> optional = pedidoService.getUsuarioByPedido(idPedido);
		
		if(optional.isEmpty()) {
			throw new PresentationException("No existe el Usuario con el idPedido " + idPedido, HttpStatus.NOT_FOUND);
		}
		
		return optional.get();
		
	}	
	
}
