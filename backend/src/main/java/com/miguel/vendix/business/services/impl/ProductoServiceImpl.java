package com.miguel.vendix.business.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.miguel.vendix.business.model.Producto;
import com.miguel.vendix.business.services.ProductoService;
import com.miguel.vendix.integration.repositories.CategoriaRepository;
import com.miguel.vendix.integration.repositories.ProductoRepository;
import com.miguel.vendix.security.model.Usuario;
import com.miguel.vendix.security.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductoServiceImpl implements ProductoService {

	private final ProductoRepository productoRepository;
	
	private final CategoriaRepository categoriaRepository;
	
	private final UsuarioRepository usuarioRepository;
	
	public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository) {
		this.productoRepository = productoRepository;
		this.categoriaRepository = categoriaRepository;
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	@Transactional 
	//Transactional: si ocurre una exception las operaciones de la base de datos se revertiran
	public Long create(Producto producto, Long idUsuario) {
		
		if(producto.getId() != null) {
			throw new IllegalStateException("Para crear un producto el id ha de ser null");
		}
		
		Usuario usuario = usuarioRepository.findById(idUsuario)
				.orElseThrow(() -> new IllegalStateException("No se ha encontrado el usuario con id ["+idUsuario+"]"));
		
		producto.setDisponible(true);
		producto.setUsuario(usuario);
		
		Producto productoCreado = productoRepository.save(producto);
		
		System.out.println(producto);
		
		return productoCreado.getId();
	}

	@Override
	public Optional<Producto> read(Long id) {
		
		Optional<Producto> optional = productoRepository.findById(id);
		
		return optional.isEmpty() ? Optional.empty() : Optional.of(optional.get());
	}

	@Override
	@Transactional
	public void update(Producto producto) {
		
		Long id = producto.getId();
		
		boolean existe = productoRepository.existsById(id);
		
		if(!existe) {
			throw new IllegalStateException("El producto con ID ["+ id +"] no existe ");
		}
		
		producto.setDisponible(true);
		
		productoRepository.save(producto);
		
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		boolean existe = productoRepository.existsById(id);
		
		if(!existe) {
			throw new IllegalStateException("El producto con ID [" + id + "] no existe.");	
		}
    
		Optional<Producto> optional = productoRepository.findById(id);
		
		optional.get().setDisponible(false); 
		
		productoRepository.save(optional.get());
	}

	@Override
	public List<Producto> getAll() {
		return productoRepository.findAll();
	}

	@Override
	public List<Producto> getBetweenPriceRange(double min, double max) {
		return productoRepository.findByPrecioBetweenOrderByPrecioDesc(min, max);
	}

	@Override
	public int getNumeroTotalProductos() {
		return (int) productoRepository.count();
	}

	@Override
	public List<Producto> getlAllByCategoria(Long idCategoria) {
		
		boolean existe = categoriaRepository.existsById(idCategoria);
		
		if(!existe) {
			new IllegalStateException("La categoria con ID [" + idCategoria + "] no existe.");
		}
		
		return productoRepository.findByCategoriaId(idCategoria);
	}

	@Override
	public List<Producto> getAllByNombre(String nombreProducto) {
	    return productoRepository.findByNombreStartingWithIgnoreCase(nombreProducto);
	}
	
	@Override
    @Transactional
    public void actualizarRutaImagen(Long productoId, String rutaImagen) {
		productoRepository.actualizarRutaImagen(productoId, rutaImagen);
    }

	@Override
	public String getRutaImagen(Long productoId) {
		return productoRepository.getRutaImagenPorId(productoId);
	}

	@Override
	public List<Producto> getAllByUsuario(Long idUsuario) {
		return productoRepository.findByUsuarioId(idUsuario);
	}



}
