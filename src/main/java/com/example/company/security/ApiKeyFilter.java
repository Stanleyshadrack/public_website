package com.example.company.security;

import com.example.company.config.InternalConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(ApiKeyFilter.class);

    private final InternalConfig config; // cleaner than @Value

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String path = req.getRequestURI();
        String ip = req.getRemoteAddr();

        // PUBLIC ROUTES → skip filter
        if (isPublicRoute(path)) {
            chain.doFilter(req, res);
            return;
        }

        // PROTECTED ROUTES
        if (isProtectedPath(path)) {

            String key = req.getHeader("X-INTERNAL-KEY");

            if (key == null || key.isBlank()) {
                logger.warn(" Missing API key | IP: {} | Path: {}", ip, path);
                respondUnauthorized(res, path, "Missing API key");
                return;
            }

            String maskedKey = maskKey(key);

            //  Constant-time comparison (security)
            if (!isValidKey(key)) {
                logger.warn(" Invalid API key [{}] | IP: {} | Path: {}", maskedKey, ip, path);
                respondUnauthorized(res, path, "Invalid API key");
                return;
            }

            //  AUTHENTICATE REQUEST (CRITICAL)
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            "internal-client",
                            null,
                            Collections.emptyList()
                    );

            SecurityContextHolder.getContext().setAuthentication(auth);

            logger.info(" Authorized [{}] | IP: {} | Path: {}", maskedKey, ip, path);
        }

        chain.doFilter(req, res);
    }

    // Constant-time comparison to prevent timing attacks
    private boolean isValidKey(String providedKey) {
        byte[] provided = providedKey.trim().getBytes();
        byte[] actual = config.getKey().getBytes();
        return MessageDigest.isEqual(provided, actual);
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

    // Protected endpoints
    private boolean isProtectedPath(String path) {
        return path.startsWith("/v1/api/careers/actions")
                || path.startsWith("/v1/api/contact/ops")
                || path.startsWith("/v1/api/faqs/manage")
                || path.startsWith("/v1/api/partners/manage")
                || path.startsWith("/v1/api/policies/manage")
                || path.startsWith("/v1/api/projects/manage");
    }

    //  Public endpoints
    private boolean isPublicRoute(String path) {
        return path.startsWith("/public")
                || path.equals("/")
                || path.equals("/favicon.ico")

                // careers public
                || path.equals("/v1/api/careers")
                || (path.startsWith("/v1/api/careers/") && !path.contains("/actions"))

                // faqs public
                || path.equals("/v1/api/faqs")
                || (path.startsWith("/v1/api/faqs/") && !path.contains("/manage"))

                // policies
                || path.equals("/v1/api/policies")
                || (path.startsWith("/v1/api/policies/") && !path.contains("/manage"))

                // partners
                || path.equals("/v1/api/partners")
                || (path.startsWith("/v1/api/partners/") && !path.contains("/manage"))

                // projects
                || path.equals("/v1/api/projects")
                || (path.startsWith("/v1/api/projects/") && !path.contains("/manage"))

                // misc public
                || path.startsWith("/swagger-ui")
                || path.startsWith("/api-docs")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/webjars")
                || path.startsWith("/h2-console")
                || path.startsWith("/api/subscription");
    }

    // Mask key for safe logging
    private String maskKey(String key) {
        if (key == null || key.length() < 8) return "****";
        return key.substring(0, 4) + "****" + key.substring(key.length() - 4);
    }
}