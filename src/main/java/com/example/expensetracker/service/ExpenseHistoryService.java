package com.example.expensetracker.service;

import com.example.expensetracker.model.ExpenseHistory;
import com.example.expensetracker.repository.ExpenseHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseHistoryService {

    private final ExpenseHistoryRepository historyRepository;

    public ExpenseHistoryService(ExpenseHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public void record(String action, String details) {
        historyRepository.save(new ExpenseHistory(action, details, LocalDateTime.now()));
    }

    public List<ExpenseHistory> getAllHistory() {
        return historyRepository.findAllByOrderByTimestampDesc();
    }
}