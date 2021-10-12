package com.example.amt_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    @GetMapping(path = "/login")
    public String login()
    {
        return "login";
    }

}
