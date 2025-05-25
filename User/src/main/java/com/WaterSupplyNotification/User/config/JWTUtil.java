package com.WaterSupplyNotification.User.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
    private static final String SECRET_KEY="ThisIsASecretKeyForJwtAuthWithAtLeast32Chars";
    private static final long expiration_time=1000*60*60;
     private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);

   private final Key signingKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String email,String role)
    {
        return Jwts.builder()
        .setSubject(email)
        .claim("role",role)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expiration_time))
                .signWith(signingKey, SignatureAlgorithm.HS256).compact();
    }

    public boolean validateToken(String token,String email)
    {
        return email.equals(extractEmail(token)) && !isTokenExpired(token);
    }

    public String extractEmail(String token)
    {
        logger.info("Token received for email extraction: {}", token);
        try {
            if(token.startsWith("Bearer "))
            {
                token = token.substring(7).trim();
            }
        return getClaims(token).getSubject();
    } catch (Exception e) {
        logger.error("Failed to extract email from token: " + token, e);
        throw e;
    }
    }

    public String extractRole(String token) {
        token=token.trim();
        return getClaims(token).get("role", String.class);
    }

    public boolean isTokenExpired(String token)
    {
        Date expirationDate = getClaims(token).getExpiration();
        return expirationDate.before(new Date());
    }

    private Claims getClaims(String token) {
        token = stripPrefix(token);
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String stripPrefix(String token) {
    if (token != null && token.startsWith("Bearer ")) {
        return token.substring(7).trim();
    }
    return token != null ? token.trim() : null;
}
}
