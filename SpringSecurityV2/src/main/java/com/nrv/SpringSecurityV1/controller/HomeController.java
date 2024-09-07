package com.nrv.SpringSecurityV1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/nrv")
    ResponseEntity<?> getNrv() {
        return ResponseEntity.ok("Wassup NRV");
    }
}
