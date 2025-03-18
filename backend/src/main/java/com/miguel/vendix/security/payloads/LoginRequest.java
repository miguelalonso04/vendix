package com.miguel.vendix.security.payloads;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
}
