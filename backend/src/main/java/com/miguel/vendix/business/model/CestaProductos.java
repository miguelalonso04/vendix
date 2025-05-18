package com.miguel.vendix.business.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "CESTA_PRODUCTOS")
public class CestaProductos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ElementCollection
    @CollectionTable(name = "cesta_productos_producto", 
                     joinColumns = @JoinColumn(name = "cesta_id"))
    @MapKeyJoinColumn(name = "producto_id")
    @Column(name = "cantidad")
    private Map<Producto, Integer> productos = new HashMap<>();
    
    private double total;
    
    @JsonAnyGetter
    public Map<Producto, Integer> getProductos() {
        return this.productos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Resumen de tu cesta:\n\n");
        double subtotal = 0;

        if (productos.isEmpty()) {
            sb.append("Tu cesta está vacía.\n");
        } else {
        	for (Producto producto : productos.keySet()) {
                int cantidad = productos.get(producto);
                subtotal = producto.getPrecio() * cantidad;

                sb.append("- ").append(producto.getNombre())
                  .append(" x").append(cantidad)
                  .append(" - ").append(String.format("%.2f", subtotal)).append(" €\n");
            }
        	sb.append("\nTotal: ").append(String.format("%.2f", total)).append(" €");
        }

        return sb.toString();
    }

    
}
