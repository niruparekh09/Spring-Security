package com.nrv;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authorization.EnableMultiFactorAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.FactorGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
// 1. Enable MFA support.
// 'authorities' defines the "badges" a user collects as they pass each step.
// - PASSWORD_AUTHORITY: Granted after Form Login.
// - OTT_AUTHORITY: Granted after clicking the magic link.
@EnableMultiFactorAuthentication(authorities = {
        FactorGrantedAuthority.PASSWORD_AUTHORITY,
        FactorGrantedAuthority.OTT_AUTHORITY
})
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((auth) -> auth
                        // 2. Public endpoints (Landing page + "Check your email" page)
                        .requestMatchers("/","/ott/sent").permitAll()
                        // 3. Admin page requires full authentication (Role + implicitly MFA if configured)
                        .requestMatchers("/admin").hasRole("ADMIN"))

                // 4. First Factor: Standard Username/Password
                .formLogin(withDefaults())

                // 5. Second Factor: One-Time Token (Magic Link)
                .oneTimeTokenLogin(withDefaults())
                .build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        // Standard in-memory users
        var user = User.withUsername("nrv").password("{noop}password").roles("USER").build();
        var adm = User.withUsername("admin").password("{noop}password").roles("ADMIN", "USER").build();
        return new InMemoryUserDetailsManager(user, adm);
    }
}
