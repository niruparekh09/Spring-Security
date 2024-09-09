package com.nrv.SpringSecurityJWT.service;

import com.nrv.SpringSecurityJWT.model.User;
import com.nrv.SpringSecurityJWT.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo repo;

    @Autowired
    JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public List<User> getUserList() {
        return repo.findAll();
    }

    @Override
    public User addUser(User newUser) {
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        return repo.save(newUser);
    }

    @Override
    public String verify(User user) {
        Authentication authentication = authManager.
                authenticate(new UsernamePasswordAuthenticationToken
                        (user.getUserName(), user.getPassword()));

        if (authentication.isAuthenticated()) return jwtService.generateToken(user.getUserName());

        return "Fail";
    }
}
