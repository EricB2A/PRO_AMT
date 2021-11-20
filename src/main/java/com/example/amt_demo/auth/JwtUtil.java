package com.example.amt_demo.auth;


import com.example.amt_demo.config.SpringSecurityConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtil {
    private final String secretKey;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public JwtUtil(@Value("${com.example.amt_demo.config.jwt.secret}") String secretKey) {
        this.secretKey = secretKey;
    }

    public JwtTokenPayload  getJwtTokenPayload(String token) throws SignatureException {

        Claims claims = Jwts.parser().setSigningKey(encodeSecret()).
                parseClaimsJws(token).getBody();

        return new JwtTokenPayload(claims.get(JwtTokenPayload.USERNAME, String.class), claims.get(JwtTokenPayload.ROLE, String.class));
    }

    private String encodeSecret() {
        return Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(encodeSecret()).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean isTokenValid(String token) {
        return (!isTokenExpired(token));
    }
}