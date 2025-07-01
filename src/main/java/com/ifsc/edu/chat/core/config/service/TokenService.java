package com.ifsc.edu.chat.core.config.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ifsc.edu.chat.core.user.domain.entities.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${system.security.jwt.secretKey}")
    private String secretKey;

    @Value("${system.security.jwt.cookieName}")
    private String cookieName;

    @Value("${system.security.jwt.issuer}")
    private String issuer;

    public String generate(User user) {
        try {
            Instant expires = generateAuthRequestExpiration();

            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getUsername())
                    .withExpiresAt(expires)
                    .sign(Algorithm.HMAC256(secretKey));
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error on create token", e);
        }
    }

    public String validate(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secretKey))
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }

    public ResponseCookie generateTokenCookie(String token) {
        return ResponseCookie.from(cookieName, token)
                .path("/")
                .maxAge(2000)
                .httpOnly(true)
                .sameSite("Strict")
                .secure(true)
                .build();
    }

    public ResponseCookie getCleanCookie() {
        return ResponseCookie.from(cookieName, "")
                .path("/")
                .build();
    }

    public String recoverToken(HttpServletRequest request) {
        String token = "";
        final Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equalsIgnoreCase(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token;
    }

    private Instant generateAuthRequestExpiration() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
