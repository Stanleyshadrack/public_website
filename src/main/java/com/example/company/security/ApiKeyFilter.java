package com.example.company.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${app.internal.key}")
    private String internalKey;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String path = req.getRequestURI();

        // Allow all public GET endpoints
        if ("GET".equals(req.getMethod()) && path.startsWith("/public")) {
            chain.doFilter(req, res);
            return;
        }

        // Protect internal management routes
        if (isProtectedPath(path)) {
            String key = req.getHeader("X-INTERNAL-KEY");

            // Validate key without logging any secrets
            if (key == null || !key.equals(internalKey)) {
                // Respond with generic unauthorized message (no sensitive info)
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.setContentType("application/json");
                res.getWriter().write("""
                    {
                      "status": 401,
                      "error": "Unauthorized",
                      "message": "Access denied. Invalid or missing API key.",
                      "path": "%s"
                    }
                    """.formatted(path));
                return;
            }
        }

        chain.doFilter(req, res);
    }

    // Helper method for protected routes
    private boolean isProtectedPath(String path) {
        return path.startsWith("/v1/api/careers/actions")
                || path.startsWith("/v1/api/contact/ops")
                || path.startsWith("/v1/api/faqs/manage")
                || path.startsWith("/v1/api/partners/manage")
                || path.startsWith("/v1/api/policies/manage")
                || path.startsWith("/v1/api/projects/manage");
    }
}
