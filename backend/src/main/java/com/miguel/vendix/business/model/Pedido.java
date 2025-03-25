package com.miguel.vendix.business.model;

import java.util.Date;

import com.miguel.vendix.security.model.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PEDIDOS")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    
    @ManyToOne
    @JoinColumn(name = "cesta_id", referencedColumnName = "id")
    private CestaProductos cestaProductos;
    
    @ManyToOne
    @JoinColumn(name = "direccion_id")
    private Direccion direccion;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    private double precioTotal;
    
    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;
    
    private Date fechaPedido;
}

