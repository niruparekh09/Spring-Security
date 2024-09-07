package com.nrv.SpringSecurityV1.controller;

import com.nrv.SpringSecurityV1.model.F1;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class F1Controller {

    private List<F1> drivers = new ArrayList<>(List.of(
            new F1(33, "Max Verstappen", 3),
            new F1(44, "Lewis Hamilton", 7)
    ));

    @GetMapping("/drivers")
    ResponseEntity<List<F1>> getDriverList() {
        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/csrf-token")
    ResponseEntity<CsrfToken> getCsrfToken(HttpServletRequest httpServletRequest){
        return ResponseEntity.ok((CsrfToken) httpServletRequest.getAttribute("_csrf"));
    }

    @PostMapping("drivers")
    ResponseEntity<F1> addADriver(@RequestBody F1 newDriver) {
        drivers.add(newDriver);
        return ResponseEntity.ok(newDriver);
    }
}
