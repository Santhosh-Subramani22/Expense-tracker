package com.example.expensetracker.util;

import com.example.expensetracker.model.Expense;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class ExpenseExcelExporter {

    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final List<Expense> listExpenses;

    public ExpenseExcelExporter(List<Expense> listExpenses) {
        this.listExpenses = listExpenses;
        this.workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Daily Passbook");
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(11);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);

        String[] headers = {"ID", "Item / Spent On", "Category", "Payment Mode", "Date", "Amount (₹)"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(10);
        style.setFont(font);

        for (Expense expense : listExpenses) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            row.createCell(columnCount++).setCellValue(expense.getId());
            row.createCell(columnCount++).setCellValue(expense.getItemSpentOn());
            row.createCell(columnCount++).setCellValue(expense.getCategory());
            row.createCell(columnCount++).setCellValue(expense.getPaymentMode());
            row.createCell(columnCount++).setCellValue(expense.getExpenseDate().toString());
            row.createCell(columnCount).setCellValue(expense.getAmount().doubleValue());
        }

        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}