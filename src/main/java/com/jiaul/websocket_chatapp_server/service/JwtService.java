package com.jiaul.websocket_chatapp_server.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtService {

    private static final long EXPIRATION_TIME = 30L *24*60*60*1000;
    private final SecretKey key;

    public JwtService() {
        String secretString = "e5sge6p9nl41sf1jw3a5rdf8gs84451sf345nw5716e4kns6ef4a5dd4a1f60kna7pfg5ifh44e3a5wf155581d51uh1f3er4s";
        byte[] keyByte = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
        this.key = new SecretKeySpec(keyByte, "HmacSHA256");
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key).compact();
    }

    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder().claims(claims).subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key).compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    public String getExpirationTime() {
        return String.valueOf(EXPIRATION_TIME);
    }

    public Date getExpirationDateTime(){
        return new Date(System.currentTimeMillis() + EXPIRATION_TIME);
    }
}
