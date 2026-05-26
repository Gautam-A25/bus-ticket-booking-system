package com.busticket.busticketbooking.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service responsible for JWT token generation, parsing, and validation.
 *
 * <p>The secret key and expiry duration are injected from {@code application.yaml}
 * (properties: {@code jwt.secret} and {@code jwt.expiration-ms}).
 * The default expiry is 24 hours (86400000 ms).</p>
 *
 * <p>Tokens are signed with HMAC-SHA256. The subject claim holds the username
 * of the authenticated user.</p>
 */
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    /**
     * Generates a JWT token for the given user with no extra claims.
     *
     * @param userDetails the authenticated user whose username becomes the token subject
     * @return a signed JWT string
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with additional custom claims.
     *
     * @param extraClaims any additional key-value pairs to embed in the token payload
     * @param userDetails the authenticated user
     * @return a signed JWT string
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())         // Username stored as subject
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the username (subject claim) from the given JWT token.
     *
     * @param token a signed JWT string
     * @return the username embedded in the token subject
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Validates the token against the given user.
     * A token is valid if its subject matches the username <em>and</em> it has not expired.
     *
     * @param token       a signed JWT string
     * @param userDetails the user to validate the token against
     * @return {@code true} if the token is valid for this user, {@code false} otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /** Returns {@code true} if the token's expiration date is before the current time. */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /** Extracts the expiration date claim from the token. */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generic helper that extracts a single claim from the token using the provided resolver function.
     *
     * @param <T>            the type of the claim value
     * @param token          the JWT string
     * @param claimsResolver a function that maps the full {@link Claims} object to the desired value
     * @return the extracted claim value
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Parses and returns all claims from the token. Throws a {@code JwtException}
     * if the signature is invalid or the token is malformed.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /** Builds the HMAC-SHA256 signing key from the configured secret string. */
    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}