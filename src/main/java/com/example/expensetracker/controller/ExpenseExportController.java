package com.example.expensetracker.controller;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.service.ExpenseService;
import com.example.expensetracker.util.ExpenseExcelExporter;
import com.example.expensetracker.util.ExpensePdfExporter;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/export")
public class ExpenseExportController {

    private final ExpenseService expenseService;

    public ExpenseExportController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=expenses_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Expense> listExpenses = expenseService.getAllExpenses();
        ExpenseExcelExporter excelExporter = new ExpenseExcelExporter(listExpenses);
        excelExporter.export(response);
    }

    @GetMapping("/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=expenses_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Expense> listExpenses = expenseService.getAllExpenses();
        ExpensePdfExporter pdfExporter = new ExpensePdfExporter(listExpenses);
        pdfExporter.export(response);
    }
}