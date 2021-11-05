package com.example.amt_demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/cart")
@Controller
public class CartController {

    @GetMapping(path="")
    public String getCart() {
        return "cart";
    }

}
