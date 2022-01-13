package com.example.amt_demo.controller;

import com.example.amt_demo.model.Purchase;
import com.example.amt_demo.service.CustomUserService;
import com.example.amt_demo.service.PurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@RequestMapping(path = "/purchase")
@Controller
@AllArgsConstructor
public class PurchaseController {
    private final CustomUserService userDetails;
    private PurchaseService purchaseService;

    private Principal getLoggedUser() {
        return (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping
    public String getPurchasesList(ModelMap mp) {
        List<Purchase> li = purchaseService.findByUser(userDetails.getUser());
        mp.addAttribute("purchases", purchaseService.findByUser(userDetails.getUser()));
        return "purchase";
    }
}
