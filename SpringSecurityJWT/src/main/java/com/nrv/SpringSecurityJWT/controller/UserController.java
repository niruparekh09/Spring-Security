package com.nrv.SpringSecurityJWT.controller;

import com.nrv.SpringSecurityJWT.model.User;
import com.nrv.SpringSecurityJWT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping
    ResponseEntity<List<User>> getUserList() {
        return ResponseEntity.ok(service.getUserList());
    }

    @PostMapping("/register")
    ResponseEntity<User> register(@RequestBody User newUser) {
        return ResponseEntity.ok(service.addUser(newUser));
    }

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody User user) {
        return ResponseEntity.ok(service.verify(user));
    }

}
