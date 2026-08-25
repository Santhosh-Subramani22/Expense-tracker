# 💰 Expense Tracker Web Application

A full-stack Personal Expense Tracker application built using **Java, Spring Boot, Thymeleaf, and MySQL**. This application helps users track daily expenses, view spending history, categorize transactions, and export reports in PDF and Excel formats.

---

## 🚀 Features

* **User Authentication & Session Management:** Secure login and registration flows with custom interceptors.
* **Expense Management (CRUD):** Add, update, view, and delete daily expenses with categories and descriptions.
* **Reports & Analytics:** View consolidated spending summaries and track monthly budgeting trends.
* **Data Export:** Export detailed transaction history directly into **PDF** and **Excel (.xlsx)** formats.
* **Responsive UI:** Clean, responsive dashboard built with Thymeleaf fragments, modern HTML5, and CSS.
* **REST APIs:** Integrated REST endpoints for fetching and handling expense data programmatically.

---

## 🛠️ Tech Stack

* **Backend:** Java 17+, Spring Boot, Spring MVC, Spring Data JPA, Hibernate
* **Frontend:** Thymeleaf, HTML5, CSS3, JavaScript
* **Database:** MySQL
* **Tools & Libraries:** Apache POI (Excel Export), iText / OpenPDF (PDF Export), Maven, Git

---

## 📁 Project Architecture

```text
src/main/java/com/example/expensetracker/
├── config/             # Configuration & Interceptors (AuthInterceptor, WebConfig)
├── controller/         # Web MVC & REST Controllers (Expense, Export, Views)
├── model/              # JPA Entities (Expense, ExpenseHistory, UserProfile)
├── repository/         # Spring Data JPA Repositories
├── service/            # Business Logic & Service Implementations
└── util/               # Export Utilities (PDF & Excel Exporters)
