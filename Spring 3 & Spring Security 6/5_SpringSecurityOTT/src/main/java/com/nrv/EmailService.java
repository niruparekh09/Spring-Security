package com.nrv;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    // 1. Inject configuration values from application.yaml
    // This prevents hardcoding sensitive keys in Java files.
    @Value("${sendgrid.api-key}")
    String sendGridApiKey;

    @Value("${from.email}")
    String fromEmail;

    /**
     * Sends a plain text email using the SendGrid API.
     *
     * @param to      The recipient's email address
     * @param subject The subject line of the email
     * @param content The body text of the email
     */
    public void sendEmail(String to, String subject, String content) throws IOException {
        // 2. Construct the email object using SendGrid's helper classes
        Email from = new Email(fromEmail);
        Email toEmail = new Email(to);
        Content emailContent = new Content("text/plain", content); // MimeType is text/plain
        Mail mail = new Mail(from, subject, toEmail, emailContent);

        // 3. Initialize the SendGrid client
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            // 4. Build and execute the HTTP POST request to SendGrid's servers
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build()); // Convert the Mail object to JSON payload

            Response response = sg.api(request);

            // 5. Logging for debugging (crucial for verifying delivery status)
            // 202 means "Accepted" (SendGrid received it, but hasn't delivered it yet)
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());
            System.out.println("Response Headers: " + response.getHeaders());
        } catch (IOException e) {
            // 6. Re-throw exception so the caller (OttSuccessHandler) knows it failed
            throw new IOException("Failed to send message: " + e);
        }
    }
}