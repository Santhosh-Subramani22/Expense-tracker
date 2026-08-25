package com.example.expensetracker.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        // Allow public assets, login views, and auth endpoints
        if (uri.startsWith("/login") || uri.startsWith("/api/auth") || uri.startsWith("/css") || uri.startsWith("/js")) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("LOGGED_IN_USER") != null) {
            return true;
        }

        response.sendRedirect("/login");
        return false;
    }
}