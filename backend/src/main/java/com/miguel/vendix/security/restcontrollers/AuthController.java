package com.miguel.vendix.security.restcontrollers;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miguel.vendix.business.model.CestaProductos;
import com.miguel.vendix.business.model.Producto;
import com.miguel.vendix.business.services.CestaProductoService;
import com.miguel.vendix.business.services.RoleService;
import com.miguel.vendix.business.services.UsuarioService;
import com.miguel.vendix.presentation.config.PresentationException;
import com.miguel.vendix.security.JwtUtils;
import com.miguel.vendix.security.model.Role;
import com.miguel.vendix.security.model.RoleEnum;
import com.miguel.vendix.security.model.Usuario;
import com.miguel.vendix.security.payloads.JwtResponse;
import com.miguel.vendix.security.payloads.LoginRequest;
import com.miguel.vendix.security.payloads.RegisterRequest;

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
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private CestaProductoService cestaService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        Authentication authentication = null;

        try {
            // Autentica al usuario con el nombre de usuario y contraseña proporcionados
            authentication = authenticationManager.authenticate(
            		new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
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
        
          return ResponseEntity.ok(new JwtResponse(jwt,
                  usuario.getId(),
                  usuario.getUsername(),
                  usuario.getEmail(),
                  roles));
          
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
    	    	
        // Verificar si el usuario ya existe
        if (usuarioService.findByUserName(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("¡El nombre de usuario ya está en uso!");
        }
        
        if(usuarioService.existsByEmail(registerRequest.getEmail())) {
        	return ResponseEntity.badRequest().body("Ya existe una cuenta asociada con ese email");
        }

        // Encriptar la contraseña antes de guardar
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        // Crear el usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(registerRequest.getUsername());
        usuario.setPassword(encodedPassword);
        usuario.setEmail(registerRequest.getEmail());
        usuario.setFirstName(registerRequest.getFirstName());
        usuario.setLastName(registerRequest.getLastName());
        usuario.setTelefono(registerRequest.getTelefono());
        usuario.setEnabled(true);

        //Asignar Rol
        Set <Role> rol = new HashSet<>();
        rol.add(roleService.findRoleByTipo(RoleEnum.USER).get());
        usuario.setRoles(rol);
        
        usuario.setLastPasswordResetDate(new Date());
        
        //Le asignamos una cesta
        CestaProductos nuevaCesta = new CestaProductos();
        nuevaCesta.setId(usuario.getId());
        nuevaCesta.setProductos(new HashMap<Producto,Integer>());
        nuevaCesta.setTotal(0);
		cestaService.create(nuevaCesta);
		
        usuarioService.register(usuario);
        
        return ResponseEntity.ok(Map.of("message", "Usuario registrado exitosamente"));
    }

}
