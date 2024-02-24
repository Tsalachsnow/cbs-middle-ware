package com.cbs.middleware.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class SecurityFilter extends OncePerRequestFilter {

//    @Value("${cbs.api.key}")
//    private String cbsKey;


    private final String cbsKey = "ER09876546789ER098";



    private final Set<String> VALID_API_KEYS = new HashSet<>();

    @PostConstruct
    public void init() {
        // Populate the set with valid API keys
        VALID_API_KEYS.add(cbsKey);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain filterChain) throws ServletException, IOException {
        init();
        String apiKey = httpRequest.getHeader("Api-Key");
        log.info("api-key:: "+ apiKey);
        if (apiKey == null || apiKey.isEmpty()) {
            // API key is missing, send unauthorized response
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            sendErrorResponse(httpResponse, HttpStatus.UNAUTHORIZED, "Api-Key Authentication needed");
            return;
        }
        // Validate API key
        if (!isValidApiKey(apiKey)) {
            // Invalid API key, send unauthorized response
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            sendErrorResponse(httpResponse, HttpStatus.UNAUTHORIZED, "Invalid Api-Key");
            return;
        }

        // API key is valid, continue with the request
        filterChain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {
    }

    private boolean isValidApiKey(String apiKey) {
        log.info("VALID_API_KEYS ::"+ VALID_API_KEYS);
        return VALID_API_KEYS.contains(apiKey);
    }

    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"statusCode\": %d, \"message\": \"%s\"}", status.value(), message));
    }
}
