package com.example.amt_demo;

import com.example.amt_demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(path = "/catalog")
@Controller
public class CatalogController {

    @Autowired
    private CarpetRepository carpetRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(path="", produces = {"application/xml"})
    public String getAllCarpets(ModelMap mp) {

          // this.generateDummyData();


        mp.addAttribute("carpets", carpetRepository.findAll());
        mp.addAttribute("categories", categoryRepository.notEmptyCategory());
        return "public/catalog";
    }



    @GetMapping(path="/{id}", produces = {"application/xml"})
    public String getCarpet(ModelMap mp, @PathVariable String id) {
        mp.addAttribute("carpet", carpetRepository.findById(Integer.valueOf(id)));
        return "public/article";
    }

    @GetMapping(path="/filter/{name}", produces = {"application/xml"})
    public String getCarpetsFilter(ModelMap mp, @PathVariable String name) {
        mp.addAttribute("carpets", carpetRepository.findByFilter(name));
        mp.addAttribute("categories", categoryRepository.notEmptyCategory());
        mp.addAttribute("filter", name);
        return "public/catalog";
    }
}