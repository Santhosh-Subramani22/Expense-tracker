package com.example.expensetracker.controller;

import com.example.expensetracker.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/api/auth/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload, HttpSession session) {
        String username = payload.get("username");
        String password = payload.get("password");

        if (authService.validateLogin(username, password)) {
            session.setAttribute("LOGGED_IN_USER", username);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Login successful",
                "user", username,
                "token", "AUTH_SESSION_" + System.currentTimeMillis()
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of(
                "success", false,
                "message", "Invalid username or password! (Default: admin / admin)"
            ));
        }
    }

    @PostMapping("/api/auth/send-otp")
    @ResponseBody
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> payload) {
        try {
            String email = payload.get("email");
            authService.generateAndSendOtp(email);
            return ResponseEntity.ok(Map.of("success", true, "message", "OTP sent successfully to " + email));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(Map.of("success", false, "message", "Failed to dispatch email."));
        }
    }

    @PostMapping("/api/auth/reset-password")
    @ResponseBody
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String otp = payload.get("otp");
        String newPassword = payload.get("newPassword");

        if (authService.resetPassword(email, otp, newPassword)) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Password successfully changed. You can now login."));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Invalid or expired OTP."));
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}