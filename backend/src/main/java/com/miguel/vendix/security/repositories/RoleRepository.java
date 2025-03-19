package com.miguel.vendix.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguel.vendix.security.model.Role;
import com.miguel.vendix.security.model.RoleEnum;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Optional<Role> findRoleByTipo(RoleEnum rol);
}
