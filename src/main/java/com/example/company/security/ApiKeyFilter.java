package com.example.company.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(ApiKeyFilter.class);

    @Value("${app.internal.key}")
    private String internalKey;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String path = req.getRequestURI();

        // ✅ Allow static/public assets and docs
        if (isPublicRoute(path)) {
            chain.doFilter(req, res);
            return;
        }

        // ✅ Allow newsletter subscriptions (public)
        if (path.startsWith("/api/subscription")) {
            chain.doFilter(req, res);
            return;
        }

        // 🔒 Protect internal management routes
        if (isProtectedPath(path)) {
            String key = req.getHeader("X-INTERNAL-KEY");

            if (key == null) {
                logger.warn("❌ Missing API key on protected path: {}", path);
                respondUnauthorized(res, path, "Missing API key");
                return;
            }

            // Masked logging (safe)
            String maskedKey = maskKey(key);

            if (!key.equals(internalKey)) {
                logger.warn(" Invalid API key [{}] on protected path: {}", maskedKey, path);
                respondUnauthorized(res, path, "Invalid API key");
                return;
            }

            // ✅ Authorized access
            logger.info("✅ Valid API key [{}] authorized for path: {}", maskedKey, path);
        }

        chain.doFilter(req, res);
    }

    private void respondUnauthorized(HttpServletResponse res, String path, String message) throws IOException {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType("application/json");

        res.getWriter().write("""
        {
          "timestamp": "%s",
          "status": 401,
          "error": "Unauthorized",
          "message": "%s",
          "path": "%s"
        }
        """.formatted(LocalDateTime.now(), message, path));
    }


    // Safely mask key for logging (e.g., YC6U****TbP)
    private String maskKey(String key) {
        if (key == null || key.length() < 8) return "****";
        return key.substring(0, 4) + "****" + key.substring(key.length() - 4);
    }

    private boolean isProtectedPath(String path) {
        return path.startsWith("/v1/api/careers/actions")
                || path.startsWith("/v1/api/contact/ops")
                || path.startsWith("/v1/api/faqs/manage")
                || path.startsWith("/v1/api/partners/manage")
                || path.startsWith("/v1/api/policies/manage")
                || path.startsWith("/v1/api/projects/manage");
    }

    private boolean isPublicRoute(String path) {
        return path.startsWith("/public")
                || path.equals("/v1/api/faqs")
                || path.startsWith("/v1/api/faqs/") && !path.contains("/manage")
                || path.equals("/v1/api/policies")
                || path.startsWith("/v1/api/policies/") && !path.contains("/manage")
                || path.equals("/v1/api/partners")
                || path.startsWith("/v1/api/partners/") && !path.contains("/manage")
                || path.equals("/v1/api/projects")
                || path.startsWith("/v1/api/projects/") && !path.contains("/manage")
                || path.equals("/v1/api/careers")
                || path.startsWith("/v1/api/careers/") && !path.contains("/actions")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/api-docs")
                || path.equals("/")
                || path.equals("/favicon.ico");
    }

}

