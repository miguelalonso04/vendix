package com.miguel.vendix.business.model.dtos;

import java.util.Date;
import java.util.List;

import com.miguel.vendix.business.model.Direccion;
import com.miguel.vendix.business.model.EstadoPedido;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
	
	private Long id;
	private List<ProductoDTO> productos;
	private Direccion direccion;
	private String nombreUsuario;
	private Double precioTotalPedido;
	private EstadoPedido estado;
	private Date fechaPedido;
}
