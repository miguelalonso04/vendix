package com.miguel.vendix.security.payloads;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String token;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    
}
