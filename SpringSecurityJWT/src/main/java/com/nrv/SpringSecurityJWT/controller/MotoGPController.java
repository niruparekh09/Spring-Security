package com.nrv.SpringSecurityJWT.controller;

import com.nrv.SpringSecurityJWT.model.MotoGP;
import com.nrv.SpringSecurityJWT.service.MotoGPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/MotoGP")
public class MotoGPController {

    @Autowired
    MotoGPService service;

    @GetMapping("/riders")
    ResponseEntity<List<MotoGP>> getRiders() {
        return ResponseEntity.ok(service.fetchAllRiders());
    }
}
