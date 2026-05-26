package com.busticket.busticketbooking.config;

import com.busticket.busticketbooking.security.CustomUserDetailsService;
import com.busticket.busticketbooking.security.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Central Spring Security configuration for the Bus Ticket Booking System.
 *
 * <p>
 * Two separate {@link SecurityFilterChain} beans are registered:
 * </p>
 * <ol>
 * <li><b>API chain (Order 1)</b> — covers {@code /api/**} and Swagger paths.
 * Uses stateless JWT auth; missing/invalid tokens return HTTP 401.</li>
 * <li><b>Web chain (Order 2)</b> — covers all remaining paths.
 * Uses session-based form login; unauthenticated users are redirected to
 * {@code /login}.</li>
 * </ol>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private final JwtFilter jwtFilter;
        private final CustomUserDetailsService customUserDetailsService;

        public SecurityConfig(
                        JwtFilter jwtFilter,
                        CustomUserDetailsService customUserDetailsService) {
                this.jwtFilter = jwtFilter;
                this.customUserDetailsService = customUserDetailsService;
        }

        /**
         * Provides a BCrypt password encoder bean used for hashing and verifying user
         * passwords.
         */
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        /**
         * Configures a DAO-based authentication provider that loads users from the
         * database
         * via {@link CustomUserDetailsService} and verifies passwords using BCrypt.
         */
        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setUserDetailsService(customUserDetailsService);
                provider.setPasswordEncoder(passwordEncoder());
                return provider;
        }

        /**
         * Exposes the {@link AuthenticationManager} bean so that service classes
         * (e.g., AuthServiceImpl) can authenticate users programmatically.
         */
        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration configuration) throws Exception {
                return configuration.getAuthenticationManager();
        }

        /**
         * <b>API Security Filter Chain (Order 1)</b> — stateless JWT authentication.
         *
         * <p>
         * Applies only to {@code /api/**}, {@code /swagger-ui/**}, and
         * {@code /v3/api-docs/**}.
         * CSRF is disabled because REST clients (Postman, mobile apps) do not send CSRF
         * tokens.
         * Unauthenticated requests receive HTTP 401 — no redirect to a login page.
         * </p>
         */
        @Bean
        @Order(1)
        public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
                // Restrict this chain to API and Swagger UI paths only
                http.securityMatcher("/api/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**");

                // Stateless REST API — CSRF protection is not required
                http.csrf(AbstractHttpConfigurer::disable);
                http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                // Auth endpoints and Swagger UI are public; all other API paths require a valid JWT
                http.authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/v1/auth/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .anyRequest().authenticated());

                http.authenticationProvider(authenticationProvider());
                // Run JWT validation before Spring's built-in username/password filter
                http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

                // Return 401 Unauthorized for missing/invalid tokens (no redirect)
                http.exceptionHandling(ex -> ex.authenticationEntryPoint(apiEntryPoint()));

                return http.build();
        }

        /**
         * <b>Web (UI) Security Filter Chain (Order 2)</b> — session-based form login.
         *
         * <p>
         * Matches all paths not covered by the API chain. Static assets, the login
         * page, and the register page are publicly accessible. All other paths require
         * an active HTTP session. Unauthenticated users are redirected to
         * {@code /login}.
         * </p>
         */
        @Bean
        @Order(2)
        public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
                // This chain is the catch-all; Order 2 means it runs after the API chain
                http.securityMatcher("/**");

                // CSRF disabled — form submissions use Spring's hidden method filter instead
                http.csrf(AbstractHttpConfigurer::disable);
                http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

                http.authorizeHttpRequests(auth -> auth
                                .requestMatchers(
                                                "/login",
                                                "/register",
                                                "/css/**", // Static CSS
                                                "/js/**", // Static JS
                                                "/images/**", // Static images
                                                "/error",
                                                "/favicon.ico",
                                                "/api/v1/auth/**",
                                                "/swagger-ui.html",
                                                "/swagger-ui/**",
                                                "/v3/api-docs/**",
                                                "/v3/api-docs")
                                .permitAll()
                                // Dashboard and all module UI pages require authentication
                                .requestMatchers("/", "/ui/**", "/modules/**").authenticated()
                                .anyRequest().authenticated());

                http.authenticationProvider(authenticationProvider());

                // Custom login page; successful login always lands on the root dashboard
                http.formLogin(form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/", true)
                                .failureUrl("/login?error=true")
                                .permitAll());

                // On logout: invalidate session, clear auth, and delete the JSESSIONID cookie
                http.logout(logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout=true")
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll());

                // Redirect unauthenticated web users to the login page instead of returning 401
                http.exceptionHandling(ex -> ex.authenticationEntryPoint(webEntryPoint()));

                return http.build();
        }

        /** Returns HTTP 401 for API requests that have no valid JWT token. */
        @Bean
        public AuthenticationEntryPoint apiEntryPoint() {
                return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                "Unauthorized");
        }

        /** Redirects unauthenticated web UI requests to the login page. */
        @Bean
        public AuthenticationEntryPoint webEntryPoint() {
                return (request, response, authException) -> response.sendRedirect("/login");
        }
}