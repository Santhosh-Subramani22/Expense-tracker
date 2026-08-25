package com.example.expensetracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    // Default In-Memory Credentials
    private String currentUsername = "admin";
    private String currentPassword = "admin";

    // Allowed Admin Recovery Emails
    private final Set<String> authorizedEmails = new HashSet<>(Arrays.asList(
        "ranjithsankar8940@gmail.com",
        "santhoshsub22@gmail.com"
    ));

    // Store OTP in-memory: key -> email, value -> OTP
    private final Map<String, String> otpStorage = new HashMap<>();

    public boolean validateLogin(String username, String password) {
        return currentUsername.equals(username) && currentPassword.equals(password);
    }

    public String generateAndSendOtp(String email) {
        String normalizedEmail = email.trim().toLowerCase();

        if (!authorizedEmails.contains(normalizedEmail)) {
            throw new IllegalArgumentException("This email is not registered for admin recovery.");
        }

        // Generate 6-digit numeric OTP
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStorage.put(normalizedEmail, otp);

        // Send Email
        try {
            if (mailSender != null) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(normalizedEmail);
                message.setSubject("DailyWallet - Your Password Reset OTP");
                message.setText("Hello,\n\nYour One-Time Password (OTP) for resetting your DailyWallet admin password is: " 
                        + otp + "\n\nThis OTP is valid for 10 minutes.\n\nBest Regards,\nDailyWallet Team");
                mailSender.send(message);
            } else {
                System.out.println(">>> (DEV MODE - SMTP NOT CONFIGURED) Generated OTP for " + normalizedEmail + " is: " + otp);
            }
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            System.out.println(">>> (FALLBACK LOG) Generated OTP is: " + otp);
        }

        return otp;
    }

    public boolean verifyOtp(String email, String otp) {
        String storedOtp = otpStorage.get(email.trim().toLowerCase());
        return storedOtp != null && storedOtp.equals(otp.trim());
    }

    public boolean resetPassword(String email, String otp, String newPassword) {
        if (verifyOtp(email, otp)) {
            this.currentPassword = newPassword;
            otpStorage.remove(email.trim().toLowerCase());
            return true;
        }
        return false;
    }
}