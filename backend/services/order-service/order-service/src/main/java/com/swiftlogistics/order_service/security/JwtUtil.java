package com.swiftlogistics.order_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;

@Component
public class JwtUtil {

    private final String jwtSecret = "supersecretkeysupersecretkeysupersecretkey";
    private final Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public String extractUserId(String token) {
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }

}