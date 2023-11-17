package com.farmdigital.nerddevs.repository;

import com.farmdigital.nerddevs.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles,Integer> {
Roles findByName(String  name);

}
