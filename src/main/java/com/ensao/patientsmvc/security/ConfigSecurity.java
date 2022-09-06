package com.ensao.patientsmvc.security;

import com.ensao.patientsmvc.security.services.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class ConfigSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //first
        //PasswordEncoder passEncoder = passwordEncoder();
//        auth.inMemoryAuthentication()
//                .withUser("user1").password(passEncoder.encode("0000")).roles("USER")
//                .and()
//                .withUser("admin").password(passEncoder.encode("1111")).roles("ADMIN","USER");

       // System.out.println(passEncoder.encode("0000"));

        //second

//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("select username as principal,password as credentials,active from users where username=?")
//                .authoritiesByUsernameQuery("select username as principal,role as role from users_role where username=?")
//                .rolePrefix("ROLE_")
//                .passwordEncoder(passEncoder);

        //third

        auth.userDetailsService(userDetailsServiceImp);




    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin(); //login form par défaut
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/user/**").hasAuthority("USER");
        // authorize les resources static comme bootstrap
        http.authorizeRequests().antMatchers("/webjars/**", "/css/**", "/js/**", "/images/**").permitAll();

        http.authorizeRequests().anyRequest().authenticated(); //toutes les requêtes sont authentifiées
        http.exceptionHandling().accessDeniedPage("/403"); //page d'erreur 403
    }

}

