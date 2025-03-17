package com.miguel.vendix.business.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
	
	private Long id;
	private List<Producto> producto;
	private Direccion direccion;
	private Usuario usuario;
	private double precioTotal;
	private EstadoPedido estado;
	private Date fechaPedido;
	
}
