package com.example.FoodDeliveryApp.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler
        implements AuthenticationSuccessHandler {

    private static final String FRONTEND_URL = System.getenv().getOrDefault(
            "FRONTEND_URL",
            "http://localhost:5173");

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        for (GrantedAuthority authority : authentication.getAuthorities()) {

            String role = authority.getAuthority();

            if (role.equals("ROLE_ADMIN")) {
                response.sendRedirect(FRONTEND_URL + "/admin/dashboard");
                return;
            }

            if (role.equals("ROLE_USER")) {
                response.sendRedirect(FRONTEND_URL + "/user/dashboard");
                return;
            }

            if (role.equals("ROLE_DELIVERY_PARTNER")) {
                response.sendRedirect(FRONTEND_URL + "/delivery/dashboard");
                return;
            }
        }

        response.sendRedirect(FRONTEND_URL + "/login?error");
    }
}