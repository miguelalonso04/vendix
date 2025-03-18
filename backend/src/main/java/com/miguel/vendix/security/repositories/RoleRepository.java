package com.miguel.vendix.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miguel.vendix.security.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
