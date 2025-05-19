package com.miguel.vendix.security.model;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Entity
@Table(name="USUARIOS", uniqueConstraints = {
		@UniqueConstraint(columnNames = "USERNAME"),
		@UniqueConstraint(columnNames = "EMAIL")
})
public class Usuario implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	private String password;

	@Email
	private String email;

	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	private String telefono;
	private Boolean enabled;
	
	@Column(name = "lastPasswordResetDate")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date lastPasswordResetDate;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="USUARIOS_ROLES",
			   joinColumns = @JoinColumn(name="ID_USER"),
			   inverseJoinColumns = @JoinColumn(name="ID_ROL"))
	private Set<Role> roles;
	
	public Usuario() {
		this.roles = new HashSet<Role>();
	}

	@Override
	public String getUsername() {
		return this.username;
	}
	
	/**
     * Devuelve los roles del usuario como `GrantedAuthority` para Spring Security.
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getTipo()))
                .collect(Collectors.toSet());
    }
    
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
    	return UserDetails.super.isAccountNonExpired();
    }
    
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
    	return UserDetails.super.isAccountNonLocked();
    }
    
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
    	return UserDetails.super.isCredentialsNonExpired();
    }
    
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
