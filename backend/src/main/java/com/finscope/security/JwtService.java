package com.finscope.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

@Service
public class JwtService {
    private final String SECRET_KEY = "finscope-secret-key-for-jwt-authentication-2026";

    /**
     * Creates the signing key used for signing and verifying JWT tokens.
     *
     * @return SecretKey for HMAC-SHA256
    */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }

    /**
     * Generates a JWT token for the authenticated user.
     *
     * @param email User's email address
     * @return Signed JWT token
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() +1000 * 60 * 60))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extracts the user's email (subject) from the JWT token.
     *
     * @param token JWT token
     * @return User's email address
     */
    public String extractEmail(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }


    /**
     * Validates whether the JWT token is authentic and not expired.
     *
     * @param token JWT token
     * @return true if the token is valid; otherwise false
    */
    public boolean isTokenValid(String token) {

        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
