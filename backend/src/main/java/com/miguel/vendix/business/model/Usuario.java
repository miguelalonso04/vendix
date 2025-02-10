package com.miguel.vendix.business.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String userName;
	private String password;

	@Email
	private String email;

	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	private String telefono;
	private Boolean enabled;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastPasswordResetDate;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="ROLES",
			   joinColumns = @JoinColumn(name="ID_USER"),
			   inverseJoinColumns = @JoinColumn(name="ID_ROL"))
	private Set<Role> roles;
	
	public Usuario() {
		this.roles = new HashSet<Role>();
	}

}
