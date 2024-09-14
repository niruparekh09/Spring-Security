package com.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//Entry point of spring sec configuration
@EnableWebSecurity // to enable web security frmwork
@Configuration // to tell SC following is java configuration class : to declare spring beans
//Equivalent to bean config xml file, This class can contain bean declaration : @Bean
//annotated methods(equivalent to <bean id , class....../>
@EnableMethodSecurity // to enable method level security , with pre auth n post auth
public class SecurityConfig {
	// dep : JWT filter
	@Autowired
	private JWTRequestFilter jwtFilter;

	// configures spring security for authorization (role based)
	@Bean
	public SecurityFilterChain authorizeRequests(HttpSecurity http, AuthenticationManager manager) throws Exception {

		return http
				// disable CORS
				.cors(AbstractHttpConfigurer::disable)
				// disable CSRF
				.csrf(AbstractHttpConfigurer::disable)
				// sesion creation : stateless
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//			        Set permissions on endpoints
				.authorizeHttpRequests(auth -> auth
//			            our public endpoints
						.requestMatchers("/products/view", "/users/signin", "/users/signup", "/swagger*/**",
								"/v*/api-docs/**")
						.permitAll().requestMatchers("/products/purchase").hasRole("CUSTOMER")
						.requestMatchers("/products/add").hasRole("ADMIN")
//			            our private endpoints
						.anyRequest().authenticated())
				.authenticationManager(manager)

//			        We need to add a our custom jwt filter before the UsernamePasswordAuthenticationFilter.
//			        Since we need every request to be authenticated before going through spring security filter.
//			        (UsernamePasswordAuthenticationFilter creates a UsernamePasswordAuthenticationToken from a username and password that are submitted in the HttpServletRequest.)
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();

	}

	// expose spring supplied auth mgr as a spring bean , so that auth controller
	// can use it for authentication .
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
