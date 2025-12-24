package com.nrv.SpringSecurityV1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        return  http
                .csrf(customizer -> customizer.disable()) // To disable csrf, so that we do not need to add token in every
                // change request i.e. POST, PUT & DELETE
                .authorizeHttpRequests(request -> request.anyRequest().authenticated()) // To enable authentication in our
                // application so no one can access any endpoint without any kind of authorisation
                .httpBasic(Customizer.withDefaults()) // To enable request from web clients like postman
                // (Otherwise you will get a Login form HTML in it despite add Basic Auth with User and Password )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // To make our API Stateless.
                .build();
        //.formLogin(Customizer.withDefaults()) To enable The default login form provided with Spring Security
        // ( you can remove this if you are using a Frontend or only a webclient).
    }
}
