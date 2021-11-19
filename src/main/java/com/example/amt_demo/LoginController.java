package com.example.amt_demo;

import com.example.amt_demo.auth.DTO.LoginDTO;
import com.example.amt_demo.auth.DTO.SignupErrorDTO;
import com.example.amt_demo.auth.UsernameJwtAuthenticationToken;
import com.example.amt_demo.service.LoginService;
import com.example.amt_demo.utils.login.LoginAPIResponse;
import com.example.amt_demo.utils.login.UserCredentialsDTO;
import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Controller
public class LoginController {
    private final String accessTokenName;
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final AuthenticationManager authenticationManager;
    private final LoginService loginService;
    private final Gson gson = new GsonBuilder().create();
    private final JsonParser jsonParser = new JsonParser();



    @Autowired
    public LoginController(AuthenticationManager authenticationManager, @Value("${com.example.amt_demo.config.jwt.cookie.name}") String accessTokenName, LoginService loginService) {
        this.authenticationManager = authenticationManager;
        this.accessTokenName = accessTokenName;
        this.loginService = loginService;
    }

    @GetMapping(path = "/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping(path = "/login")
    public String login(HttpServletResponse response, LoginDTO login, ModelMap mp) {
        try {
            logger.info(login.toString());
            UsernameJwtAuthenticationToken token = (UsernameJwtAuthenticationToken)
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(token);
            Cookie cookie = new Cookie(accessTokenName, token.getToken());
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return "home";
        } catch (AuthenticationException ignored) {
            mp.addAttribute("badcredential", true);
        } catch (Exception e) {
            mp.addAttribute("error", true);
        }
        return "login";
    }

    @PostMapping(path = "/inscription")
    public String getSignup(UserCredentialsDTO userCredentialsDTO, ModelMap mp) {
        try {
            LoginAPIResponse authResponse = loginService.registerUser(userCredentialsDTO);
            if (authResponse.getStatusCode() == HttpStatus.CREATED.value()) {
                return "redirect:/login?signup=success";
            }
        } catch (HttpClientErrorException.Conflict e) {
            mp.addAttribute("userAlreadyExists", true);
        } catch (HttpClientErrorException.UnprocessableEntity e) {
            JsonObject elem = jsonParser.parse(e.getResponseBodyAsString()).getAsJsonObject();
            Arrays.stream(gson.fromJson( elem.getAsJsonArray(), SignupErrorDTO[].class))
                    .forEach(error -> mp.put(error.getProperty(), error.getMessage()));
            mp.addAttribute("errorform", true);
        } catch (Exception ignored) {
            mp.addAttribute("error", true);
        }
        return "signup";
    }

    @PostMapping(path = "/deconnexion")
    public String logout(HttpServletResponse response) {
        loginService.logout();
        Cookie cookie = new Cookie(accessTokenName, "");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return "home";
    }

    @GetMapping(path = "/inscription")
    public String signup() {
        return "signup";
    }
}
