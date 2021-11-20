/**
 * @team AMT - Silkyroad
 * @authors Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file JwtRequestFilter.java
 *
 * @brief TODO
 */

package com.example.amt_demo.auth;

import com.example.amt_demo.service.CustomUserDetails;
import com.example.amt_demo.service.CustomUserDetailsServiceImpl;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CustomUserDetailsServiceImpl userDetails;
    private final JwtUtil jwtUtil;
    final private String cookieName;

    /**
     *
     * @param userDetails
     * @param jwtUtil
     * @param cookieName
     */
    @Autowired
    public JwtRequestFilter(CustomUserDetailsServiceImpl userDetails, JwtUtil jwtUtil, @Value("${com.example.amt_demo.config.jwt.cookie.name}") String cookieName) {
        logger.info(cookieName);
        this.userDetails = userDetails;
        this.jwtUtil = jwtUtil;
        this.cookieName = cookieName;
    }

    /**
     *
     * @param request
     * @param response
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        Optional<Cookie> accessCookie = null;
        Cookie[] requestCookies = request.getCookies();

        if (requestCookies != null) {
            accessCookie = Arrays.stream(request.getCookies()).filter(cookie ->
                    cookie.getName().equals(cookieName)).findFirst();
        }

        JwtTokenPayload jwtPayload = null;
        String jwt = null;
        try{
            if (accessCookie != null && accessCookie.isPresent() && !accessCookie.get().getValue().isEmpty()) {
                jwt = accessCookie.get().getValue();
                jwtPayload = jwtUtil.getJwtTokenPayload(jwt);
            }

            if (jwtPayload != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetails.loadUserByUsername(jwtPayload.getUsername());
                if (jwtUtil.isTokenValid(jwt)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, AuthorityUtils.createAuthorityList(CustomUserDetails.ROLE_PREFIX + jwtPayload.getRole()));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } catch(SignatureException ignored) {

        }
        chain.doFilter(request, response);
    }
}