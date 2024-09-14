package com.app.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.jwt_utils.JwtUtils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
//custom filter
public class JWTRequestFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils utils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// check for authorization hdr
		String authHeadr = request.getHeader("Authorization");
		if (authHeadr != null && authHeadr.startsWith("Bearer")) {
			System.out.println("got bearer token");
			String token = authHeadr.substring(7);
			Claims claims = utils.validateJwtToken(token);
			// extract subject from the token
			String email = utils.getUserNameFromJwtToken(claims);
			// extract authorities from the token
			List<GrantedAuthority> authorities = utils.getAuthoritiesFromClaims(claims);
			// wrap user details (username/email +granted authorities ) in the
			// username pwd token
			UsernamePasswordAuthenticationToken authentication = 
					new UsernamePasswordAuthenticationToken(email, null,
					authorities);
			//save above auth object in the spring sec ctx
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		} else
			System.out.println("req did not contain any bearer token");
		filterChain.doFilter(request, response);// passing the control to the nexyt filter in the chain

	}

}
