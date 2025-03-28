package com.miguel.vendix.business.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

	private String nombre;
	private Double precio;
	private Integer cantidad;
	
}
