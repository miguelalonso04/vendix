package com.miguel.vendix.business.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String telefono;
	
}
