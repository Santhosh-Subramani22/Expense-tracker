package com.example.expensetracker.util;

import com.example.expensetracker.model.Expense;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class ExpensePdfExporter {

    private final List<Expense> listExpenses;

    public ExpensePdfExporter(List<Expense> listExpenses) {
        this.listExpenses = listExpenses;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(new Color(59, 102, 255));
        cell.setPadding(6);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
        font.setColor(Color.WHITE);

        String[] headers = {"ID", "Item / Shop", "Category", "Payment Mode", "Date", "Amount"};
        for (String header : headers) {
            cell.setPhrase(new Phrase(header, font));
            table.addCell(cell);
        }
    }

    private void writeTableData(PdfPTable table) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 9);
        BigDecimal total = BigDecimal.ZERO;

        for (Expense exp : listExpenses) {
            table.addCell(new Phrase(String.valueOf(exp.getId()), font));
            table.addCell(new Phrase(exp.getItemSpentOn(), font));
            table.addCell(new Phrase(exp.getCategory(), font));
            table.addCell(new Phrase(exp.getPaymentMode(), font));
            table.addCell(new Phrase(exp.getExpenseDate().toString(), font));
            table.addCell(new Phrase("₹ " + exp.getAmount().toString(), font));

            total = total.add(exp.getAmount());
        }

        PdfPCell totalLabel = new PdfPCell(new Phrase("Total Spent", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
        totalLabel.setColspan(5);
        totalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalLabel.setPadding(6);
        table.addCell(totalLabel);

        PdfPCell totalVal = new PdfPCell(new Phrase("₹ " + total.toString(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
        totalVal.setPadding(6);
        table.addCell(totalVal);
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new Color(30, 36, 56));
        Paragraph title = new Paragraph("Daily Expense Passbook Statement", fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        title.setSpacingAfter(15);
        document.add(title);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.0f, 3.0f, 2.5f, 2.0f, 2.0f, 2.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);
        document.close();
    }
}