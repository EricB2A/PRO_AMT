package com.example.amt_demo;

import com.example.amt_demo.auth.UsernameJwtAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@Controller
public class LoginController {
    private final String accessTokenName;
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, @Value("${com.example.amt_demo.config.jwt.cookie.name}") String accessTokenName) {
        this.authenticationManager = authenticationManager;
        this.accessTokenName = accessTokenName;
    }


    @GetMapping(path = "/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping(path = "/login")
    public String login(HttpServletResponse response) {
        UsernameJwtAuthenticationToken token = (UsernameJwtAuthenticationToken) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("frank", "Pa$$w0rd"));
        // TODO réupérer le formulaire vérifier qu'il existe bien
        SecurityContextHolder.getContext().setAuthentication(token);
        Cookie cookie = new Cookie(accessTokenName, token.getToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return "home";
    }
}
