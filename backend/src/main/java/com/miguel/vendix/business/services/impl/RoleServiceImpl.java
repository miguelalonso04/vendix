package com.miguel.vendix.business.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miguel.vendix.business.services.RoleService;
import com.miguel.vendix.security.model.Role;
import com.miguel.vendix.security.model.RoleEnum;
import com.miguel.vendix.security.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Optional<Role> findRoleByTipo(RoleEnum rol) {
		return roleRepository.findRoleByTipo(rol);
	}

}
