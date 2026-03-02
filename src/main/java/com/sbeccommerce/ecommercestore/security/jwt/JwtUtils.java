package com.sbeccommerce.ecommercestore.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import javax.xml.datatype.Duration;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    public static final int MAX_AGE_SECONDS = 60 * 60 * 24;
    private Long jwtExpirationMs;
    private String jwtSecret;
    private SecretKey signingKey;
    private String jwtCookie;

    public JwtUtils(@Value("${spring.app.jwtExpirationMs}") Long jwtExpirationMs, @Value("${spring.app.jwtSecret}") String jwtSecret, @Value("${spring.app.jwtCookieName}") String jwtCookie) {
        this.jwtExpirationMs = jwtExpirationMs;
        this.jwtSecret = jwtSecret;
        this.jwtCookie = jwtCookie;
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null) {
            throw new IllegalArgumentException("Missing Authorization header");
        }

        logger.debug("Authorization Header: {}", authorizationHeader);

        String[] parts = authorizationHeader.trim().split("\\s+");
        if (parts.length != 2 || !parts[0].equalsIgnoreCase("Bearer")) {
            throw new IllegalArgumentException("Invalid Authorization header: " + parts[0]);
        }

        return parts[1];
    }

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);

        if (cookie != null) {
            return cookie.getValue();
        }

        return null;
    }

    public ResponseCookie generateJwtCookie(String username) {
        String jwt = generateTokenFromUsername(username);
        return ResponseCookie.from(jwtCookie, jwt)
                .path("/api")
                .maxAge(MAX_AGE_SECONDS)
                .httpOnly(true)
                .build();
    }

    public ResponseCookie generateJwtCookie(UserDetails userPrinciple) {
        return generateJwtCookie(userPrinciple.getUsername());
    }

    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from(jwtCookie, "")
                .path("/api")
                .maxAge(0)
                .httpOnly(true)
                .build();
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date().getTime() + jwtExpirationMs)))
                .signWith(key())
                .compact();
    }

    public String generateTokenFromUsername(UserDetails userDetails) {
        return generateTokenFromUsername(userDetails.getUsername());
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public SecretKey key() {
        return signingKey;
    }

    @PostConstruct
    public void init() {
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {

        try {
            logger.debug("JwtUtils: Validate");
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
            
        } catch (MalformedJwtException exception) {
            logger.debug("Invalid JWT token: {}", exception.getMessage());
        } catch (ExpiredJwtException exception) {
            logger.debug("JWT token is expired: {}", exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            logger.debug("JWT token is unsupported: {}", exception.getMessage());
        } catch (IllegalArgumentException exception) {
            logger.debug("JWT claims string is empty: {}", exception.getMessage());
        } catch (SignatureException exception) {
            logger.debug("Invalid JWT signature: {}", exception.getMessage());
        }

        return false;
    }

}
