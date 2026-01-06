package com.fooddelivery.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fooddelivery.demo.security.JwtFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth

                // PUBLIC
                .requestMatchers(
                    "/auth/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**"
                ).permitAll()

                // USER
                .requestMatchers("/cart/**").hasRole("USER")
                .requestMatchers("/orders/my").hasRole("USER")
                .requestMatchers("/orders/place-from-cart").hasRole("USER")

                // ADMIN FOOD
                .requestMatchers("/admin/food/**").hasRole("ADMIN")

                // FOOD LIST
                .requestMatchers("/foods/**").authenticated()

                // ADMIN ORDERS
                .requestMatchers("/orders/admin/**").hasRole("ADMIN")

                // DELIVERY
                .requestMatchers("/delivery/**").hasRole("DELIVERY")

                // SUPER ADMIN
                .requestMatchers("/super-admin/**").hasRole("SUPER_ADMIN")

                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}