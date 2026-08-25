package com.example.expensetracker.controller;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.service.ExpenseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ExpenseViewController {

    private final ExpenseService expenseService;

    public ExpenseViewController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/")
    public String viewDashboard(Model model) {
        List<Expense> expenses = expenseService.getAllExpenses();

        // 1. Total Monthly Pocket Allowance (e.g. ₹20,000)
        BigDecimal monthlyPocketMoney = new BigDecimal("25000.00");

        // 2. Total Money Spent
        BigDecimal totalSpent = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 3. Current Pocket Balance Left
        BigDecimal balanceInPocket = monthlyPocketMoney.subtract(totalSpent);
        if (balanceInPocket.compareTo(BigDecimal.ZERO) < 0) {
            balanceInPocket = BigDecimal.ZERO;
        }

        // 4. Today's Total Spend
        LocalDate today = LocalDate.now();
        BigDecimal todaySpent = expenses.stream()
                .filter(e -> today.equals(e.getExpenseDate()))
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Category-wise totals for daily budget cards
        BigDecimal foodSpent = expenses.stream()
                .filter(e -> "Food & Dining".equalsIgnoreCase(e.getCategory()))
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal travelSpent = expenses.stream()
                .filter(e -> "Daily Travel".equalsIgnoreCase(e.getCategory()))
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal billsSpent = expenses.stream()
                .filter(e -> "Bills & Recharge".equalsIgnoreCase(e.getCategory()))
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("expenses", expenses);
        model.addAttribute("monthlyPocketMoney", monthlyPocketMoney);
        model.addAttribute("totalSpent", totalSpent);
        model.addAttribute("balanceInPocket", balanceInPocket);
        model.addAttribute("todaySpent", todaySpent);
        model.addAttribute("foodSpent", foodSpent);
        model.addAttribute("travelSpent", travelSpent);
        model.addAttribute("billsSpent", billsSpent);

        return "index";
    }
}