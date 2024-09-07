package com.nrv.SpringSecurityV2.service;

import com.nrv.SpringSecurityV2.model.User;

import java.util.List;

public interface UserService {
    List<User> getUserList();
    User addUser(User newUser);
}
