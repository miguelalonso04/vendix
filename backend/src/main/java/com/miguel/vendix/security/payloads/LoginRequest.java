package com.miguel.vendix.security.payloads;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotBlank
	private String username;
	
	@NotBlank
	private String password;
}
