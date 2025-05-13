package com.example.swapSafe.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Set;

@Component
public class JwtUtil {
    private final Key key;


    public JwtUtil(@Value("${spring.security.oauth2.resourceserver.jwt.secret}") String secret) {

        try {
            this.key = Keys.hmacShaKeyFor(secret.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Error initializing JwtUtil", e);
        }
    }



    public String generateToken(Long id, String email, Set<String> roles) {
        return Jwts.builder()
                .setSubject(email)
                .claim("uid", id)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86_400_000)) // 24h
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }
    public Long extractUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)  
                .parseClaimsJws(token)
                .getBody();
        return claims.get("uid", Long.class);
    }

    public String extractEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}
