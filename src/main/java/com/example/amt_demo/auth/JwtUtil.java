/**
 * @team AMT - Silkyroad
 * @authors Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file JwtUtil.java
 * @brief TODO
 */

package com.example.amt_demo.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtil {
    private final String secretKey;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *
     * @param secretKey
     */
    @Autowired
    public JwtUtil(@Value("${com.example.amt_demo.config.jwt.secret}") String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     *
     * @param token
     * @return
     */
    public JwtTokenPayload getJwtTokenPayload(String token) throws SignatureException {
        Claims claims = Jwts.parser().setSigningKey(encodeSecret()).
                parseClaimsJws(token).getBody();

        return new JwtTokenPayload((Integer) claims.get(JwtTokenPayload.ID), claims.get(JwtTokenPayload.USERNAME, String.class), claims.get(JwtTokenPayload.ROLE, String.class));
    }

    /**
     *
     * @return
     */
    private String encodeSecret() {
        return Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     *
     * @param token
     * @return
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     *
     * @param token
     * @param claimsResolver
     * @param <T>
     * @return
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     *
     * @param token
     * @return
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(encodeSecret()).parseClaimsJws(token).getBody();
    }

    /**
     *
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     *
     * @param token
     * @return
     */
    public Boolean isTokenValid(String token) {
        return (!isTokenExpired(token));
    }
}