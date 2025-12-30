package com.CloudKeeper.CloudBalanceBackend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String SECRET = "this-is-my-cloudbalance-application's-jwt-secret-which-you-can't-crack-HAHA!-TATA :??865676@#$%77@$%^";
    private final long EXPIRY = 15 * 60 * 1000L;
    private SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getAuthorities().iterator().next().getAuthority());

        return Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRY)).signWith(secretKey, SignatureAlgorithm.HS256).compact();
    }

    //    extract emailId from jwd token
    public Claims ClaimExtraction(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, UserDetails user) {
        String emailId = emailIdExtraction(token);
        return emailId.equals(user.getUsername()) && !isTokenExpired(token);
    }

    public String emailIdExtraction(String token) {
        return ClaimExtraction(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return ClaimExtraction(token).getExpiration().before(new Date());
    }
}
