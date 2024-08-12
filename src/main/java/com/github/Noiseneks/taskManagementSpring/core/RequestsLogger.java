package com.github.Noiseneks.taskManagementSpring.core;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestsLogger extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestsLogger.class);

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {
        LOGGER.info("Respond to request {} {}", request.getMethod(), request.getRequestURI());
        filterChain.doFilter(request, response);
    }
}
