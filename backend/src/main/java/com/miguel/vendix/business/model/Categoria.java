package com.miguel.vendix.business.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode	
public class Categoria {

	private Long id;
	private String nombre;
	private String descripcion;
	
}
