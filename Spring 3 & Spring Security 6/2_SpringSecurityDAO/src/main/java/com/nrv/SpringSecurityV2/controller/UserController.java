package com.nrv.SpringSecurityV2.controller;

import com.nrv.SpringSecurityV2.model.User;
import com.nrv.SpringSecurityV2.service.UserService;
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

    @PostMapping
    ResponseEntity<User> addUser(@RequestBody User newUser) {
        return ResponseEntity.ok(service.addUser(newUser));
    }
}
