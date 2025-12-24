package com.nrv.SpringSecurityV2.service;

import com.nrv.SpringSecurityV2.model.User;
import com.nrv.SpringSecurityV2.model.UserPrincipal;
import com.nrv.SpringSecurityV2.repository.UserRepo;
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
