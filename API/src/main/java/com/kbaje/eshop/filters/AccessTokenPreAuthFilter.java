package com.kbaje.eshop.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kbaje.eshop.services.AccessTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

public class AccessTokenPreAuthFilter extends OncePerRequestFilter {
    
    @Autowired
    private AccessTokenProvider accessTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String accessToken = authHeader.substring(7);
            Authentication authentication = accessTokenProvider.getAuthentication(accessToken);
            if (authentication != null) {
                request.setAttribute("authentication", authentication);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
