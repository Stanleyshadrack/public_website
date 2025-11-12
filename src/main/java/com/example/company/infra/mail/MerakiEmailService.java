package com.example.company.infra.mail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MerakiEmailService {

    private static final Logger logger = LoggerFactory.getLogger(MerakiEmailService.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public MerakiEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // --- OTP EMAIL ---
    @Async
    public void sendOTPEmail(String to, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject("Your Meraki Systems Verification Code");
            message.setText("Dear User,\n\n"
                    + "Your verification code is: " + otp + "\n\n"
                    + "This code will expire in 3 minutes.\n"
                    + "If you didn’t request this, please ignore this email.\n\n"
                    + "Best regards,\n"
                    + "Meraki Systems Support\n"
                    + "https://merakisystems.co.ke");

            mailSender.send(message);
            logger.info("OTP email sent successfully to {}", to);
        } catch (Exception e) {
            logger.error("Failed to send OTP email to {}: {}", to, e.getMessage());
        }
    }

    // --- PASSWORD CHANGE CONFIRMATION ---
    @Async
    public void sendPasswordChangeConfirmationEmail(String email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setSubject("Password Changed Successfully - Meraki Systems");
            message.setText("Hi there,\n\n"
                    + "Your password has been changed successfully.\n\n"
                    + "If this wasn’t you, please contact our support team immediately at support@merakisystems.co.ke.\n\n"
                    + "Best,\n"
                    + "Meraki Systems Security Team\n"
                    + "https://merakisystems.co.ke");

            mailSender.send(message);
            logger.info("Password change confirmation email sent successfully to {}", email);
        } catch (Exception e) {
            logger.error("Failed to send password change confirmation email to {}: {}", email, e.getMessage());
        }
    }

    // --- PASSWORD RESET CONFIRMATION ---
    @Async
    public void sendPasswordResetConfirmationEmail(
            @NotBlank(message = "Email is required")
            @Email(message = "Please provide a valid email") String email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setSubject("Password Reset Successful - Meraki Systems");
            message.setText("Hello,\n\n"
                    + "Your password has been reset successfully.\n\n"
                    + "If this wasn’t initiated by you, please contact us immediately at support@merakisystems.co.ke.\n\n"
                    + "Thank you for choosing Meraki Tech.\n"
                    + "https://merakisystems.co.ke");

            mailSender.send(message);
            logger.info("Password reset confirmation email sent successfully to {}", email);
        } catch (Exception e) {
            logger.error("Failed to send password reset confirmation email to {}: {}", email, e.getMessage());
        }
    }

    // --- CONTACT EMAIL ---
    @Async
    public void sendContactEmail(String fromName, String fromEmail, String subject, String messageBody) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(this.fromEmail);
            message.setTo("support@merakisystems.co.ke");
            message.setSubject("New Contact Message: " + subject);
            message.setText("You have received a new message from the Meraki Systems website.\n\n"
                    + "Name: " + fromName + "\n"
                    + "Email: " + fromEmail + "\n\n"
                    + "Message:\n" + messageBody + "\n\n"
                    + "---------------------------------\n"
                    + "Meraki Systems Web Platform\n"
                    + "https://merakisystems.co.ke");

            mailSender.send(message);
            logger.info("Contact email received from {} <{}>", fromName, fromEmail);
        } catch (Exception e) {
            logger.error("Failed to send contact email from {}: {}", fromEmail, e.getMessage());
        }
    }


    // --- SUBSCRIPTION LINK AND GUIDE ---
    @Async
    public void sendSubscriptionGuideEmail(String to) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject("Activate Your Meraki Systems Subscription");

            message.setText("""
            Hello,

            Thank you for choosing Meraki Systems! We're excited to have you onboard.

            🔹 *Please Note*
            1️⃣ Your subscription is now active.
            2️⃣ You can start receiving premium updates and system alerts immediately.
            3️⃣ Explore all our features on the Meraki Systems platform.

            Need help? Visit our Help Center or contact support at support@merakisystems.co.ke.

            Best regards,
            The Meraki Systems Team
            https://merakisystems.co.ke
            """);

            mailSender.send(message);
            logger.info("Subscription guide email sent successfully to {}", to);

        } catch (Exception e) {
            logger.error("Failed to send subscription guide email to {}: {}", to, e.getMessage());
        }
    }



    @Async
    public void sendUnsubscribeConfirmationEmail(String to) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject("You've been unsubscribed - Meraki Systems");
            message.setText("Hi,\n\n"
                    + "You have been successfully unsubscribed from the Meraki Systems newsletter.\n"
                    + "You can re-subscribe anytime at https://merakisystems.co.ke.\n\n"
                    + "Best,\n"
                    + "The Meraki Systems Team");

            mailSender.send(message);
            logger.info("Unsubscribe confirmation email sent to {}", to);
        } catch (Exception e) {
            logger.error("Failed to send unsubscribe email to {}: {}", to, e.getMessage());
        }
    }

}
