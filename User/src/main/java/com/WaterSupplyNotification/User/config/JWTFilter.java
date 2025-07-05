package com.WaterSupplyNotification.User.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;

public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    @Value("${internal.service.token}")
    private String internalServiceToken;

    public JWTFilter(JWTUtil jwtUtil)
    {
        this.jwtUtil=jwtUtil;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = token.substring(7).trim();

        if (token.equals(internalServiceToken)) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                "internal-service", null, Collections.emptyList()
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String email = jwtUtil.extractEmail(token);
            if (email != null && jwtUtil.validateToken(token, email)) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                email, null, Collections.emptyList() // Or extract roles if available
               );
            SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            } catch (ExpiredJwtException e) {
                   response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
                   return;
            } catch (MalformedJwtException | SignatureException e) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                    return;
            }  

        filterChain.doFilter(request, response);
    }
}
