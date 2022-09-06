package com.ensao.patientsmvc.security.repositories;

import com.ensao.patientsmvc.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,String> {

    AppUser findByUsername(String username);
}
