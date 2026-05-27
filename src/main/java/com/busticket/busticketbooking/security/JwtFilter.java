package com.busticket.busticketbooking.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Servlet filter that intercepts every HTTP request exactly once and
 * validates any JWT token it finds.
 *
 * <p>The filter looks for a JWT in two places (in priority order):</p>
 * <ol>
 *   <li>The {@code Authorization: Bearer <token>} request header (used by REST clients).</li>
 *   <li>A cookie named {@code BTB_JWT} (used by the Thymeleaf web UI).</li>
 * </ol>
 *
 * <p>If a valid token is found and the Security Context does not already contain
 * an authentication object, the filter populates the context so the request
 * proceeds as fully authenticated.</p>
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    /** Name of the cookie that carries the JWT in the web UI session. */
    private static final String COOKIE_NAME = "BTB_JWT";

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtFilter(JwtService jwtService, CustomUserDetailsService customUserDetailsService) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Step 1: Extract the JWT from header or cookie
        String jwt = extractToken(request);

        // No token found — pass the request down the chain without authentication
        if (jwt == null || jwt.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        String username;

        try {
            // Step 2: Parse the username from the token claims
            username = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            // Token is malformed or signature is invalid — continue as unauthenticated
            filterChain.doFilter(request, response);
            return;
        }

        // Step 3: If the user is identified and not already authenticated, validate and set auth
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Build an authentication token with the user's authorities
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,                          // Credentials are null after authentication
                                userDetails.getAuthorities()
                        );

                // Attach request details (IP address etc.) to the auth token
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Register the authentication in the Security Context for this request
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Step 4: Continue the filter chain regardless of authentication outcome
        filterChain.doFilter(request, response);
    }

    /**
     * Attempts to extract a JWT from the current HTTP request.
     *
     * <p>Checks, in order:</p>
     * <ol>
     *   <li>{@code Authorization: Bearer <token>} header (REST API clients).</li>
     *   <li>The {@value COOKIE_NAME} cookie (Thymeleaf web UI).</li>
     * </ol>
     *
     * @return the raw JWT string, or {@code null} if none was found
     */
    private String extractToken(HttpServletRequest request) {
        // Check the Authorization header first (standard REST approach)
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);  // Strip the "Bearer " prefix
        }

        // Fall back to checking cookies (used by the Thymeleaf UI after login)
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (COOKIE_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}