package com.nrv.SpringSecurityJWT.config;

import com.nrv.SpringSecurityJWT.service.JWTService;
import com.nrv.SpringSecurityJWT.service.NrvUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    ApplicationContext context;//Central interface to provide configuration for an application.
    @Autowired
    private JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Bearer eyJhbGciOiJIUzI1NiJ9.abcabcbacbacbababcadjafkapopekfamIjoxNzI1Nzk2NDgxLCJleHAiOjE3MjU3OTgyODF9.Ib4rjpYGyU9iQez-MHQjppA4Z30WouGn-RR5OD3gQmY
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer")) {
            token = authHeader.substring(7); // Token will come after 'Bearer '
            username = jwtService.extractUserName(token); // Method to extract from the token

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { //https://www.baeldung.com/get-user-in-spring-security

                // Retrieving Bean by Type and getting the user Details from our custom user detail service
                UserDetails userDetails = context.getBean(NrvUserDetailService.class).loadUserByUsername(username);

                if (jwtService.validateToken(token, userDetails)) {
                    //An Authentication implementation that is designed for simple presentation of a username and password.
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    //convert an instance of HttpServletRequest class into an instance of the WebAuthenticationDetails class
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken); // Setting the authentication token in context
                }
            }

        }
        filterChain.doFilter(request,response);
    }
}
