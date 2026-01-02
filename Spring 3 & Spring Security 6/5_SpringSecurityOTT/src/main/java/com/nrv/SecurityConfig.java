package com.nrv;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 1. Define URL authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/ott/sent").permitAll()
                        .requestMatchers("/login/ott").permitAll()
                        .anyRequest().authenticated())
                // 2. Enable standard Username/Password form login (optional, but good for fallback)
                .formLogin(Customizer.withDefaults())
                // 3. Enable One-Time Token Login
                .oneTimeTokenLogin(Customizer.withDefaults())
                .build();
    }


    // 4. Create a hardcoded user for testing purposes
    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        // User: nrv16, Password: password
        var nrv = User.withUsername("nrv16").password("{noop}password").build();
        return new InMemoryUserDetailsManager(nrv);
    }
}
