package com.nrv;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.authentication.ott.RedirectOneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OttSuccessHandler implements OneTimeTokenGenerationSuccessHandler {

    // We use a built-in redirect handler to send the user to the "/ott/sent" page after generating the token
    private final OneTimeTokenGenerationSuccessHandler redirectHandler = new RedirectOneTimeTokenGenerationSuccessHandler("/ott/sent");

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken) throws IOException, ServletException {

        // 1. construct the "Magic Link"
        // UrlUtils.buildFullRequestUrl(request) gets the base URL (e.g., http://localhost:8080)
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(UrlUtils.buildFullRequestUrl(request))
                .replacePath(request.getContextPath())  // Ensure we respect context path if any
                .replaceQuery(null)                     // Clear existing query params
                .fragment(null)                         // Clear existing fragments
                .path("/login/ott")                     // The default Spring Security endpoint to CONSUME the token
                .queryParam("token", oneTimeToken.getTokenValue()); // Append the generated token

        String magicLink = builder.toUriString();

        // 2. SIMULATE sending email (Integration point for SendGrid later)
        System.out.println("------------------------------------------------");
        System.out.println("Username: " + oneTimeToken.getUsername());
        System.out.println("MagicLink: " + magicLink);
        System.out.println("------------------------------------------------");

        // 3. Perform the redirect to the "Email Sent" UI page
        this.redirectHandler.handle(request, response, oneTimeToken);
    }
}
