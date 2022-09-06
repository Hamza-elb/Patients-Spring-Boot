package com.ensao.patientsmvc.security.repositories;

import com.ensao.patientsmvc.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {

    AppRole findByRoleName(String roleName);
}
