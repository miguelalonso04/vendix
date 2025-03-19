package com.miguel.vendix.business.services;

import java.util.Optional;

import com.miguel.vendix.security.model.Role;
import com.miguel.vendix.security.model.RoleEnum;

public interface RoleService {
	
	Optional<Role> findRoleByTipo(RoleEnum rol);

}
