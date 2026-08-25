package com.example.expensetracker.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemSpentOn; // e.g., Tea & Snacks, Swiggy, Petrol, Groceries

    @Column(nullable = false)
    private String category; // Food & Dining, Daily Travel, Shopping, Bills & Recharge

    @Column(nullable = false)
    private String paymentMode; // UPI / GPay, Cash, Debit Card

    @Column(nullable = false)
    private LocalDate expenseDate;

    @Column(nullable = false)
    private BigDecimal amount;

    private String note;

    public Expense() {}

    public Expense(String itemSpentOn, String category, String paymentMode, LocalDate expenseDate, BigDecimal amount, String note) {
        this.itemSpentOn = itemSpentOn;
        this.category = category;
        this.paymentMode = paymentMode;
        this.expenseDate = expenseDate;
        this.amount = amount;
        this.note = note;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getItemSpentOn() { return itemSpentOn; }
    public void setItemSpentOn(String itemSpentOn) { this.itemSpentOn = itemSpentOn; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }
    public LocalDate getExpenseDate() { return expenseDate; }
    public void setExpenseDate(LocalDate expenseDate) { this.expenseDate = expenseDate; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}