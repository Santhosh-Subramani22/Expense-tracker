package com.example.expensetracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String role;
    private String currency; // EUR (€), USD ($), INR (₹)
    private boolean emailNotifications;
    private boolean monthlyReportAlert;

    public UserProfile() {}

    public UserProfile(String fullName, String email, String role, String currency, boolean emailNotifications, boolean monthlyReportAlert) {
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.currency = currency;
        this.emailNotifications = emailNotifications;
        this.monthlyReportAlert = monthlyReportAlert;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public boolean isEmailNotifications() { return emailNotifications; }
    public void setEmailNotifications(boolean emailNotifications) { this.emailNotifications = emailNotifications; }
    public boolean isMonthlyReportAlert() { return monthlyReportAlert; }
    public void setMonthlyReportAlert(boolean monthlyReportAlert) { this.monthlyReportAlert = monthlyReportAlert; }
}