package com.miguel.vendix.business.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.miguel.vendix.business.model.Categoria;
import com.miguel.vendix.business.services.CategoriaService;
import com.miguel.vendix.integration.repositories.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoriaServiceImpl implements CategoriaService{

	private CategoriaRepository categoriaRepository;
	
	public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}
	
	@Override
	@Transactional
	public Long create(Categoria categoria) {
		
		if(categoria.getId() != null) {
			throw new IllegalStateException("Para crear una categoria el id ha de ser null");
		}
		
		Categoria categoriaCreada = categoriaRepository.save(categoria);
		
		return categoriaCreada.getId();
	}

	@Override
	public Optional<Categoria> read(Long id) {
	
		Optional <Categoria> optional = categoriaRepository.findById(id);
		
		return optional.isEmpty() ? Optional.empty() : Optional.of(optional.get());
	}

	@Override
	@Transactional
	public void update(Categoria categoria) {
		
		Long id = categoria.getId();
		
		boolean existe = categoriaRepository.existsById(id);
		
		if(!existe) {
			throw new IllegalStateException("La categoria con ID ["+ id +"] no existe ");
		}
		
		categoriaRepository.save(categoria);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		boolean existe = categoriaRepository.existsById(id);
		
		if(!existe) {
			throw new IllegalStateException("La categoria con ID ["+ id +"] no existe ");
		}
		
		Optional<Categoria> optional = categoriaRepository.findById(id);
		
		categoriaRepository.delete(optional.get());
	}

	@Override
	public List<Categoria> getAll() {
		return categoriaRepository.findAll();
	}

}
