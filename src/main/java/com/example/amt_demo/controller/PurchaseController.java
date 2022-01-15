/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file PurchaseController.java
 *
 * @brief Controller for the Purchase section
 */

package com.example.amt_demo.controller;

import com.example.amt_demo.service.CustomUserService;
import com.example.amt_demo.service.PurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(path = "/purchase")
@Controller
@AllArgsConstructor
public class PurchaseController {
    private final CustomUserService userDetails;
    private PurchaseService purchaseService;

    @GetMapping
    public String getPurchasesList(ModelMap mp) {
        mp.addAttribute("purchases", purchaseService.findByUser(userDetails.getUser()));
        return "purchase";
    }
}
