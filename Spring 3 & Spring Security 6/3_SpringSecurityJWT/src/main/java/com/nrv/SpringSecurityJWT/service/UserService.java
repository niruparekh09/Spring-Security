package com.nrv.SpringSecurityJWT.service;

import com.nrv.SpringSecurityJWT.model.User;

import java.util.List;

public interface UserService {
    List<User> getUserList();
    User addUser(User newUser);

    String verify(User user);
}
