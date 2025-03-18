package com.miguel.vendix.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.miguel.vendix.security.model.Usuario;
import com.miguel.vendix.security.repositories.UsuarioRepository;


@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private UsuarioRepository usuarioRepository;
    
    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
    	this.usuarioRepository = usuarioRepository;
    }

    /**
     * Carga un usuario desde la base de datos por su nombre de usuario.
     * 
     * @param username El nombre de usuario a buscar.
     * @return Un objeto UserDetails que contiene la información del usuario para Spring Security.
     * @throws UsernameNotFoundException si el usuario no es encontrado en la base de datos.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Busca el usuario en la base de datos a través del repositorio.
        Usuario usuario = usuarioRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));

        // Convierte el usuario obtenido en una instancia de UserDetailsImpl para que Spring Security pueda manejarlo.
        return usuario;
    }
}
