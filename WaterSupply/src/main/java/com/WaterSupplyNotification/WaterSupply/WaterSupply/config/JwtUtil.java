package com.WaterSupplyNotification.WaterSupply.WaterSupply.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.stereotype.Component;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "ThisIsASecretKeyForJwtAuthWithAtLeast32Chars"; // Store this securely

    public String generateToken(String username,String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token)
                .getSubject();
    }

    public String extractRole(String token) {
        return getClaims(token)
                .get("role",String.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // private boolean isTokenExpired(String token) {
    //     return Jwts.parser()
    //             .setSigningKey(SECRET_KEY)
    //             .parseClaimsJws(token)
    //             .getBody()
    //             .getExpiration()
    //             .before(new Date());
    // }
}
