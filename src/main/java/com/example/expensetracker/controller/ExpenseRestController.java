package com.example.expensetracker.controller;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.service.ExpenseHistoryService;
import com.example.expensetracker.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseRestController {

    private final ExpenseService expenseService;
    private final ExpenseHistoryService historyService;

    public ExpenseRestController(ExpenseService expenseService, ExpenseHistoryService historyService) {
        this.expenseService = expenseService;
        this.historyService = historyService;
    }

    @GetMapping
    public List<Expense> getAll() {
        return expenseService.getAllExpenses();
    }

    @PostMapping
    public Expense create(@RequestBody Expense expense) {
        if (expense.getPaymentMode() == null || expense.getPaymentMode().isEmpty()) {
            expense.setPaymentMode("UPI / GPay");
        }
        Expense saved = expenseService.saveExpense(expense);
        
        // Log activity into History
        historyService.record(
            "SPENT",
            "Paid ₹" + saved.getAmount() + " for " + saved.getItemSpentOn() + " (" + saved.getCategory() + ") via " + saved.getPaymentMode()
        );
        
        return saved;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Expense exp = expenseService.getExpenseById(id);
        if (exp != null) {
            historyService.record("DELETED", "Removed expense entry: " + exp.getItemSpentOn() + " (-₹" + exp.getAmount() + ")");
        }
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}