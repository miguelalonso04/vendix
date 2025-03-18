package com.miguel.vendix.security.restcontrollers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miguel.vendix.business.services.UsuarioService;
import com.miguel.vendix.presentation.config.PresentationException;
import com.miguel.vendix.security.JwtUtils;
import com.miguel.vendix.security.model.Usuario;
import com.miguel.vendix.security.payloads.JwtResponse;
import com.miguel.vendix.security.payloads.LoginRequest;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

	private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        Authentication authentication = null;

        try {
            // Autentica al usuario con el nombre de usuario y contraseña proporcionados
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch(Exception e) {
            // Si la autenticación falla, registra el error y lanza una excepción con código 401 (Unauthorized)
            logger.error("Error de autenticación para el usuario {}", loginRequest.getUsername());
            throw new PresentationException("Credenciales inválidas.", HttpStatus.UNAUTHORIZED);
        }

        // Guarda la autenticación en el contexto de seguridad de Spring
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Genera un token JWT para el usuario autenticado
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Obtiene los detalles del usuario autenticado
        Usuario usuario = (Usuario) authentication.getPrincipal();

        // Extrae los roles del usuario y los convierte en una lista de strings
        List<String> roles = usuario.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        usuario.setLastPasswordResetDate(new Date());
		usuarioService.register(usuario);
        
          return ResponseEntity.ok(new JwtResponse(jwt,
                  usuario.getId(),
                  usuario.getUsername(),
                  usuario.getEmail(),
                  roles));
          
    }
}
