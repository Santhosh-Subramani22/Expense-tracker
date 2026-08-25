package com.example.expensetracker.controller;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.model.UserProfile;
import com.example.expensetracker.service.ExpenseHistoryService;
import com.example.expensetracker.service.ExpenseService;
import com.example.expensetracker.service.UserProfileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class FeatureViewController {

    private final ExpenseService expenseService;
    private final ExpenseHistoryService historyService;
    private final UserProfileService profileService;

    public FeatureViewController(ExpenseService expenseService, ExpenseHistoryService historyService, UserProfileService profileService) {
        this.expenseService = expenseService;
        this.historyService = historyService;
        this.profileService = profileService;
    }

    @GetMapping("/reports")
    public String viewReports(Model model) {
        List<Expense> expenses = expenseService.getAllExpenses();

        Map<String, BigDecimal> categoryTotals = expenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, Expense::getAmount, BigDecimal::add)));

        Map<String, Long> paymentModeCounts = expenses.stream()
                .collect(Collectors.groupingBy(Expense::getPaymentMode, Collectors.counting()));

        model.addAttribute("budgetTotals", categoryTotals);
        model.addAttribute("statusCounts", paymentModeCounts);
        model.addAttribute("totalExpenses", expenses.size());
        model.addAttribute("currentSection", "reports");
        return "reports";
    }

    @GetMapping("/history")
    public String viewHistory(Model model) {
        model.addAttribute("historyList", historyService.getAllHistory());
        model.addAttribute("currentSection", "history");
        return "history";
    }

    @GetMapping("/profile")
    public String viewProfile(Model model) {
        List<Expense> expenses = expenseService.getAllExpenses();
        BigDecimal totalSpent = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("profile", profileService.getCurrentProfile());
        model.addAttribute("totalSpent", totalSpent);
        model.addAttribute("entryCount", expenses.size());
        model.addAttribute("currentSection", "profile");
        return "profile"; // Loads profile.html
    }

    @GetMapping("/settings")
    public String viewSettings(Model model) {
        model.addAttribute("profile", profileService.getCurrentProfile());
        model.addAttribute("currentSection", "settings");
        return "settings"; // Loads settings.html
    }

    @PostMapping("/profile/update")
    public String saveProfile(@ModelAttribute("profile") UserProfile profile) {
        profileService.updateProfile(profile);
        historyService.record("UPDATED", "Updated profile identity details");
        return "redirect:/profile?success";
    }

    @PostMapping("/settings/update")
    public String saveSettings(@ModelAttribute("profile") UserProfile profile) {
        profileService.updateProfile(profile);
        historyService.record("UPDATED", "Updated wallet preferences and settings");
        return "redirect:/settings?saved";
    }
}