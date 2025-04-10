package com.sakila.sakila_project.infrastructure.config;

import com.sakila.sakila_project.infrastructure.filters.JwtSecurityFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private JwtSecurityFilter jwtSecurityFilter;

    @Autowired
    public SecurityConfiguration(JwtSecurityFilter jwtSecurityFilter) {
        this.jwtSecurityFilter = jwtSecurityFilter;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain JwtAuthSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                //TODO: Implement most specific configuration for CSRF AND CORS
                .csrf(conf -> conf.disable())
                .cors(conf -> conf.disable())
                .securityMatcher("/staff/**")
                .authorizeHttpRequests(request ->{
                    request.requestMatchers("/staff/open/**").permitAll();
                    request.anyRequest().authenticated();
                })
                .exceptionHandling(ex ->{
                    ex.authenticationEntryPoint(customAuthenticationEntryPoint());
                    ex.accessDeniedHandler(customAccessDeniedHandler());
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(conf -> conf.disable())
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain GeneralUnauthorizedFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(conf -> conf.disable())
                .securityMatcher("/**")
                .authorizeHttpRequests(request ->
                        request.anyRequest().permitAll())
                .httpBasic(conf -> conf.disable())
                .build();
    }

   //TODO: it may be necessary to implement a custom password decoder

    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return (request, response, authException) ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No authorized access");
    }

    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) ->
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
    }

}
