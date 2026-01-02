package com.nrv;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.authentication.ott.RedirectOneTimeTokenGenerationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Component
public class OttSuccessHandler implements OneTimeTokenGenerationSuccessHandler {

    // Helper to redirect user to the "Check your email" UI page
    private final OneTimeTokenGenerationSuccessHandler redirectHandler =
            new RedirectOneTimeTokenGenerationSuccessHandler("/ott/sent");

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken) throws IOException, ServletException {
        // 1. "Clean" way to build the link using current context
        String magicLink = ServletUriComponentsBuilder.fromContextPath(request)
                .replacePath(null)      // Safety: Ensure we don't duplicate paths
                .path("/login/ott")     // The URL that verifies the token
                .queryParam("token", oneTimeToken.getTokenValue())
                .toUriString();

        // 2. Simulate sending the email (Replace with SendGrid/JavaMail in production)
        System.out.println("magicLink: " + magicLink);

        // 3. Finalize: Send user to the static "Sent" page
        this.redirectHandler.handle(request, response, oneTimeToken);
    }
}