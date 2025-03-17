package com.miguel.vendix.business.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ValoracionesProducto {
	
	private Long id;
	private Long idProducto;
	private String comentario;
	private int valoracion;
	

}
