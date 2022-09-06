package com.ensao.patientsmvc.security.services;

import com.ensao.patientsmvc.security.entities.AppRole;
import com.ensao.patientsmvc.security.entities.AppUser;
import com.ensao.patientsmvc.security.repositories.AppRoleRepository;
import com.ensao.patientsmvc.security.repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SecurityServiceImpl implements SecurityService {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveNewUser(String username, String password, String verifyPassword) {
        if(!password.equals(verifyPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        String hashedPassword = passwordEncoder.encode(password);
        AppUser appUser = new AppUser();
        appUser.setUseerId(UUID.randomUUID().toString());
        appUser.setUsername(username);
        appUser.setPassword(hashedPassword);
        appUser.setActive(true);
       AppUser saved = appUserRepository.save(appUser);
        return saved;
    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if(appRole != null) {
            throw new IllegalArgumentException("Role already exists");
        }

        appRole = new AppRole();
        appRole.setRoleName(roleName);
        appRole.setDescription(description);
       AppRole savedApprole =  appRoleRepository.save(appRole);
        return savedApprole;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null) {
            throw new RuntimeException("User does not exist");
        }
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if (appRole == null) {
            throw new RuntimeException("Role does not exist");
        }
        appUser.getAppRoles().add(appRole);
        //appUserRepository.save(appUser);   //pas nécessaire grace a @Transactional
    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null) {
            throw new RuntimeException("User does not exist");
        }
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if (appRole == null) {
            throw new RuntimeException("Role does not exist");
        }
        appUser.getAppRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
