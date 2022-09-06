package com.ensao.patientsmvc;

import com.ensao.patientsmvc.entities.Patient;
import com.ensao.patientsmvc.repositories.PatientRepository;
import com.ensao.patientsmvc.security.services.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientsMvcApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //@Bean
    CommandLineRunner runner(PatientRepository patientRepository) {
        return args -> {
            patientRepository.save(new Patient(null,"Hamza",new Date(),false,101));
            patientRepository.save(new Patient(null,"Simo",new Date(),true,200));
            patientRepository.save(new Patient(null,"Bassma",new Date(),false,500));
            patientRepository.save(new Patient(null,"Rabab",new Date(),true,400));

            patientRepository.findAll().forEach(patient -> {
                System.out.println(patient.getNom());
            });
        };
    }
   // @Bean
    CommandLineRunner saveUsers(SecurityService securityService) {
        return args -> {
            securityService.saveNewUser("hamza", "123456", "123456");
            securityService.saveNewUser("simo", "123456", "123456");
            securityService.saveNewUser("rabab", "123456", "123456");

            securityService.saveNewRole("USER", "");
            securityService.saveNewRole("ADMIN", "");

            securityService.addRoleToUser("hamza", "USER");
            securityService.addRoleToUser("hamza", "ADMIN");
            securityService.addRoleToUser("simo", "USER");
            securityService.addRoleToUser("rabab", "USER");

        };
    }

}
