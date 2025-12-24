package com.nrv.SpringSecurityV2.service;

import com.nrv.SpringSecurityV2.model.User;
import com.nrv.SpringSecurityV2.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo repo;

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
}
