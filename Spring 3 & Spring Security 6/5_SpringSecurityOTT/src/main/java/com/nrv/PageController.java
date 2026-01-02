package com.nrv;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Simple page controller that exposes two endpoints used in the example:
 * - GET / -> index page (protected in the security config)
 * - GET /ott/sent -> a public page telling the user that a magic link was sent
 */
@Controller
public class PageController {

    @GetMapping("")
    public String home() {
        // Home page (requires authentication per SecurityConfig)
        return "index";
    }


    @GetMapping("/ott/sent")
    public String sent() {
        // Public page displayed after a one-time token has been generated
        return "sent";
    }
}
