package com.nrv.SpringSecurityJWT.service;

import com.nrv.SpringSecurityJWT.model.User;
import com.nrv.SpringSecurityJWT.model.UserPrincipal;
import com.nrv.SpringSecurityJWT.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NrvUserDetailService implements UserDetailsService {

    @Autowired
    UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repo.findByUserName(username);

        if (user == null) throw new UsernameNotFoundException("User Not Found");

        return new UserPrincipal(user);
    }
}
