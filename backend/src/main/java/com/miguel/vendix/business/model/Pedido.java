package com.miguel.vendix.business.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.miguel.vendix.security.model.Usuario;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyJoinColumn;
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
 
    private Double precioTotal;
    
    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date fechaPedido;

    @ElementCollection
    @CollectionTable(name = "pedido_productos", joinColumns = @JoinColumn(name = "pedido_id"))
    @MapKeyJoinColumn(name = "producto_id")
    @Column(name = "cantidad")
    private Map<Producto, Integer> productos = new HashMap<>();

	public Pedido(Long id, CestaProductos cestaProductos, Direccion direccion, Usuario usuario, Double precioTotal,
			EstadoPedido estado, Date fechaPedido) {
		this.id = id;
		this.cestaProductos = cestaProductos;
		this.direccion = direccion;
		this.usuario = usuario;
		this.precioTotal = precioTotal;
		this.estado = estado;
		this.fechaPedido = fechaPedido;
	}
    
    
}

