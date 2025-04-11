package com.miguel.vendix.business.model.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CestaDTO {
	
	private Long id;
	private Double total;
	private List<ProductoDTO> productos;

}
