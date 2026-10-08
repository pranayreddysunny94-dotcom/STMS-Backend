package com.example.stms_backend.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // ==========================================
    // PASSWORD ENCODER
    // ==========================================

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ==========================================
    // SECURITY FILTER CHAIN
    // ==========================================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

            // ==========================================
            // CSRF
            // ==========================================

            .csrf(csrf -> csrf.disable())

            // ==========================================
            // CORS
            // ==========================================

            .cors(cors -> cors.configurationSource(
                    corsConfigurationSource()
            ))

            // ==========================================
            // SESSION MANAGEMENT
            // ==========================================

            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    )
            )

            // ==========================================
            // AUTHORIZATION
            // ==========================================

            .authorizeHttpRequests(auth -> auth

                    // ==========================================
                    // CORS PREFLIGHT
                    // ==========================================

                    .requestMatchers(
                            HttpMethod.OPTIONS,
                            "/**"
                    ).permitAll()

                    // ==========================================
                    // PUBLIC
                    // ==========================================

                    .requestMatchers(
                            "/",
                            "/api/auth/**",
                            "/error"
                    ).permitAll()

                    // ==========================================
                    // USER MANAGEMENT
                    // ==========================================

                    .requestMatchers(
                            "/user/**",
                            "/api/user/**"
                    )
                    .hasAnyAuthority(
                            "ADMIN",
                            "ROLE_ADMIN"
                    )

                    // ==========================================
                    // STUDENTS
                    // ==========================================

                    .requestMatchers("/api/students/**")
                    .hasAnyAuthority(
                            "STUDENT",
                            "ROLE_STUDENT",
                            "TRAINER",
                            "ROLE_TRAINER",
                            "ADMIN",
                            "ROLE_ADMIN"
                    )

                    // ==========================================
                    // TRAINERS
                    // ==========================================

                    .requestMatchers("/api/trainers/**")
                    .hasAnyAuthority(
                            "TRAINER",
                            "ROLE_TRAINER",
                            "ADMIN",
                            "ROLE_ADMIN"
                    )

                    // ==========================================
                    // ADMIN
                    // ==========================================

                    .requestMatchers("/api/admin/**")
                    .hasAnyAuthority(
                            "ADMIN",
                            "ROLE_ADMIN"
                    )

                    // ==========================================
                    // TRAINING PROGRAMS
                    // ==========================================

                    .requestMatchers("/training-programs/**")
                    .hasAnyAuthority(
                            "STUDENT",
                            "ROLE_STUDENT",
                            "TRAINER",
                            "ROLE_TRAINER",
                            "ADMIN",
                            "ROLE_ADMIN"
                    )

                    .requestMatchers("/api/training-programs/**")
                    .hasAnyAuthority(
                            "STUDENT",
                            "ROLE_STUDENT",
                            "TRAINER",
                            "ROLE_TRAINER",
                            "ADMIN",
                            "ROLE_ADMIN"
                    )

                    // ==========================================
                    // TRAINING MODULES
                    // ==========================================

                    .requestMatchers("/training-modules/**")
                    .hasAnyAuthority(
                            "STUDENT",
                            "ROLE_STUDENT",
                            "TRAINER",
                            "ROLE_TRAINER",
                            "ADMIN",
                            "ROLE_ADMIN"
                    )

                    .requestMatchers("/api/training-modules/**")
                    .hasAnyAuthority(
                            "STUDENT",
                            "ROLE_STUDENT",
                            "TRAINER",
                            "ROLE_TRAINER",
                            "ADMIN",
                            "ROLE_ADMIN"
                    )

                    // ==========================================
                    // ASSESSMENTS
                    // ==========================================

                    .requestMatchers("/api/assessments/**")
                    .hasAnyAuthority(
                            "STUDENT",
                            "ROLE_STUDENT",
                            "TRAINER",
                            "ROLE_TRAINER",
                            "ADMIN",
                            "ROLE_ADMIN"
                    )

                    // ==========================================
                    // QUESTIONS
                    // ==========================================

                    .requestMatchers("/api/questions/**")
                    .hasAnyAuthority(
                            "STUDENT",
                            "ROLE_STUDENT",
                            "TRAINER",
                            "ROLE_TRAINER",
                            "ADMIN",
                            "ROLE_ADMIN"
                    )

                    // ==========================================
                    // RESULTS
                    // ==========================================

                    .requestMatchers("/api/results/**")
                    .hasAnyAuthority(
                            "STUDENT",
                            "ROLE_STUDENT",
                            "TRAINER",
                            "ROLE_TRAINER",
                            "ADMIN",
                            "ROLE_ADMIN"
                    )

                    // ==========================================
                    // ASSIGNMENTS
                    // ==========================================

                    .requestMatchers("/api/assignments/**")
                    .hasAnyAuthority(
                            "STUDENT",
                            "ROLE_STUDENT",
                            "TRAINER",
                            "ROLE_TRAINER",
                            "ADMIN",
                            "ROLE_ADMIN"
                    )

                    // ==========================================
                    // ASSIGNMENT SUBMISSIONS
                    // ==========================================

                    .requestMatchers("/api/assignment-submissions/**")
                    .hasAnyAuthority(
                            "STUDENT",
                            "ROLE_STUDENT",
                            "TRAINER",
                            "ROLE_TRAINER",
                            "ADMIN",
                            "ROLE_ADMIN"
                    )

                    // ==========================================
                    // ATTENDANCE
                    // ==========================================

                    .requestMatchers("/api/attendance/**")
                    .hasAnyAuthority(
                            "STUDENT",
                            "ROLE_STUDENT",
                            "TRAINER",
                            "ROLE_TRAINER",
                            "ADMIN",
                            "ROLE_ADMIN"
                    )

                    // ==========================================
                    // ENROLLMENTS
                    // ==========================================

                    .requestMatchers("/api/enrollments/**")
                    .hasAnyAuthority(
                            "STUDENT",
                            "ROLE_STUDENT",
                            "TRAINER",
                            "ROLE_TRAINER",
                            "ADMIN",
                            "ROLE_ADMIN"
                    )

                    .requestMatchers("/enrollments/**")
                    .hasAnyAuthority(
                            "STUDENT",
                            "ROLE_STUDENT",
                            "TRAINER",
                            "ROLE_TRAINER",
                            "ADMIN",
                            "ROLE_ADMIN"
                    )

                    // ==========================================
                    // ANY OTHER REQUEST
                    // ==========================================

                    .anyRequest().authenticated()
            )

            // ==========================================
            // JWT FILTER
            // ==========================================

            .addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    // ==========================================
    // CORS CONFIGURATION
    // ==========================================

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        // ==========================================
        // ALLOWED FRONTENDS
        // ==========================================

        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "https://stms-frontend-2xe4.onrender.com"
        ));

        // ==========================================
        // ALLOWED METHODS
        // ==========================================

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "PATCH",
                "OPTIONS"
        ));

        // ==========================================
        // ALLOWED HEADERS
        // ==========================================

        configuration.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "Accept",
                "Origin",
                "X-Requested-With"
        ));

        // ==========================================
        // EXPOSED HEADERS
        // ==========================================

        configuration.setExposedHeaders(List.of(
                "Authorization"
        ));

        // ==========================================
        // CREDENTIALS
        // ==========================================

        configuration.setAllowCredentials(true);

        // ==========================================
        // PREFLIGHT CACHE
        // ==========================================

        configuration.setMaxAge(3600L);

        // ==========================================
        // REGISTER CORS CONFIGURATION
        // ==========================================

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}