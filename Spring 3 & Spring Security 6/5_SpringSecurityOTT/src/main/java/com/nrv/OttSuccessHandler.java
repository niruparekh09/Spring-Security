package com.nrv;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.authentication.ott.RedirectOneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OttSuccessHandler implements OneTimeTokenGenerationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(OttSuccessHandler.class);

    // 1. Delegate Handler: Used at the very end to redirect the user to the "Sent" page
    // Using this avoids writing raw HttpServletResponse.sendRedirect() code manually.
    private final OneTimeTokenGenerationSuccessHandler redirectHandler = new RedirectOneTimeTokenGenerationSuccessHandler("/ott/sent");

    // 2. Constructor Injection: We inject the EmailService we defined above
    private final EmailService emailService;

    public OttSuccessHandler(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * This method runs automatically after Spring generates the OTT.
     *
     * @param oneTimeToken Contains the generated token value and username.
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken) throws IOException, ServletException {

        // 3. Build the "Magic Link" dynamically
        // Using UriComponentsBuilder ensures the link works on localhost, dev, or prod automatically.
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(UrlUtils.buildFullRequestUrl(request))
                .replacePath(request.getContextPath())  // Keeps the app context (e.g., /myapp) if it exists
                .replaceQuery(null)                     // Removes ?username=nrv16 from original request
                .fragment(null)
                .path("/login/ott")                     // Point to the login processing endpoint
                .queryParam("token", oneTimeToken.getTokenValue()); // Add the sensitive token

        String magicLink = builder.toUriString();

        // 4. Debugging Block (Useful for local testing if SendGrid quota is exceeded)
        System.out.println("------------------------------------------------");
        System.out.println("Username: " + oneTimeToken.getUsername());
        System.out.println("MagicLink: " + magicLink);
        System.out.println("------------------------------------------------");

        // 5. Create the Email Body using Java Text Blocks (Java 15+)
        var body = """
                Hello From NRV Secure APP
                
                This is your OTT Link for login: %s
                """.formatted(magicLink);

        // 6. Attempt to send the email
        try {
            var sendTo = oneTimeToken.getUsername(); // Currently "nrv16"

            // Call the service, converting username -> email first
            emailService.sendEmail(
                    getEmail(sendTo),
                    "One Time Token Login",
                    body
            );
        } catch (IOException e) {
            // 7. Error Handling: Log the error but DO NOT crash the request.
            // We still want to redirect the user to the "Sent" page so they don't see a 500 error page.
            log.error("Getting Error: {}", e.getMessage());
        }

        // 8. Final Step: Redirect user's browser to /ott/sent
        this.redirectHandler.handle(request, response, oneTimeToken);
    }

    /**
     * Helper method to resolve usernames to email addresses.
     * In a real app, this would query the UserRepository.
     */
    public String getEmail(String userName) {
        log.info("Getting email for: {}", userName);
        // Hardcoded mapping for development/testing
        return "nrv.pzy@gmail.com";
    }
}