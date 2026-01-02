package com.nrv;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Hello, This is NRV!";
    }

    // Protected: User needs to complete BOTH Form Login AND OTT to reach here
    @GetMapping("/admin")
    public String admin() {
        return "Hello, This is Admin!";
    }

    // Public: Shown after OTT is generated
    @GetMapping("/ott/sent")
    public String ottSent() {
        return "OTT sent to you!";
    }
}
