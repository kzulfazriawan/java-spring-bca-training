package com.springdemo.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.springdemo.securities.UserSecurityDetailAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JWTTokenUtil {
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000;

    @Value("${jwt.secret}")
    private String JWT_SECRET_KEY;

    public String tokenGenerator(Authentication authentication) {
        UserSecurityDetailAuth userSecurityDetailAuth = (UserSecurityDetailAuth) authentication.getPrincipal();

        return JWT.create()
                .withSubject("auth")
                .withClaim("email", userSecurityDetailAuth.getUsername())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusMillis(EXPIRE_DURATION))
                .sign(Algorithm.HMAC256(JWT_SECRET_KEY));
    }

    public String getDataFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(JWT_SECRET_KEY))
                .build()
                .verify(token)
                .getClaim("email").asString();
    }

    public Boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(JWT_SECRET_KEY))
                    .build()
                    .verify(token);
            return true;
        } catch(JWTVerificationException e) {
            return false;
        }
    }
}
