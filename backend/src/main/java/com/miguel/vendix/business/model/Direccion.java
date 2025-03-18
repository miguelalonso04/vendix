package com.miguel.vendix.business.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.miguel.vendix.security.model.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "DIRECCION")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_USER")
    private Usuario usuario;

    private String calle;

    private String ciudad;

    private String provincia;

    @Column(name = "CODIGO_POSTAL")
    private String codigoPostal;

    private String pais;
}

