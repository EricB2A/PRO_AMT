package com.example.amt_demo;

import com.example.amt_demo.model.CarpetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private CarpetRepository carpetRepository;

    @GetMapping(value={"/accueil","/"})
    public String index(ModelMap mp)
    {
        mp.addAttribute("articles", carpetRepository.findAll());
        return "home";
    }


}
