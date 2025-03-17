package com.miguel.vendix.business.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class CestaProductos {

	private Long id;
	private List<Producto> productos;
	private double total;
}
