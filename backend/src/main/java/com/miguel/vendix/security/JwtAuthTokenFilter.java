package com.miguel.vendix.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthTokenFilter extends OncePerRequestFilter { 

	 @Autowired
	 private JwtUtils jwtUtils;
	 
	 @Autowired
	 private UserDetailsServiceImpl userDetailsService;
	 
	 @Override
	 protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		 
		 try {
			 
			 String jwt = parseJwt(request); //Obtiene el token de la cabecera de autentificación.
			 
			 if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				 
				 String username = jwtUtils.getUserNameFromJwtToken(jwt);
				 UserDetails userDetails = userDetailsService.loadUserByUsername(username); //Cargamos datos del usuario.
				 
				 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	             authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //Añade los detalles de la solicitud.
	             
	             SecurityContextHolder.getContext().setAuthentication(authentication); //Guarda la autenticación en el contexto de seguridad.
			 }
			 
		 } catch(UsernameNotFoundException e) {
			 logger.error("No se puede identificar al usuario del token proporcionado: ", e);
		 
		 } catch (Exception e) {
			 logger.error("No se puede establecer la autenticación del usuario: ", e);
			 
		 }
		 
		 filterChain.doFilter(request, response);
		 
	 }
	 
	 @Override
	 public boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
	     String path = request.getServletPath();
	     return path.equals("/auth/signin") || path.equals("/auth/signup");
	 }
	 
	 // *************************************************************************************
	 //
	 // PRIVATE METHODS
	 //
	 // *************************************************************************************

	 private String parseJwt(HttpServletRequest request) { //Busca el token en la cabecera
	    	
		String headerAuth = request.getHeader("Authorization"); 
	        
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			 return headerAuth.substring(7);
		}
		
		return null;
	}

}