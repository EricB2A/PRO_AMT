/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file HomeController.java
 *
 * @brief Controller of the Home
 */

package com.example.amt_demo.controller;

import com.example.amt_demo.model.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ArticleRepository articleRepository;

    /**
     *
     * @param mp
     * @return
     */
    @GetMapping(value={"/accueil","/"})
    public String index(ModelMap mp) {
        mp.addAttribute("articles", articleRepository.findAll());
        return "home";
    }
}