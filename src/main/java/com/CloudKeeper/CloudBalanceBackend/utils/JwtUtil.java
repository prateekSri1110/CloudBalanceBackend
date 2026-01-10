package com.CloudKeeper.CloudBalanceBackend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expiry;

    public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiry}") long expiry) {
        this.expiry = expiry;
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getAuthorities().iterator().next().getAuthority());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiry))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    //    extract emailId from jwd token
    public Claims claimExtraction(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, UserDetails user) {
        String emailId = emailIdExtraction(token);
        return emailId.equals(user.getUsername()) && !isTokenExpired(token);
    }

    public String emailIdExtraction(String token) {
        return claimExtraction(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return claimExtraction(token).getExpiration().before(new Date());
    }
}
