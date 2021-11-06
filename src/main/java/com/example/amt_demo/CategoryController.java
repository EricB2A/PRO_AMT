package com.example.amt_demo;

import com.example.amt_demo.model.Carpet;
import com.example.amt_demo.model.CarpetRepository;
import com.example.amt_demo.model.Category;
import com.example.amt_demo.model.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(path = "/admin")
@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CarpetRepository carpetRepository;

    @GetMapping(path="/all", produces = {"application/xml"})
    public String getAllCategories(ModelMap mp) {
        mp.addAttribute("categories", categoryRepository.findAll());
        mp.addAttribute("carpets", carpetRepository.findAll());
        return "admin";
    }

    @GetMapping("/new")
    public String getCarpetRepository(ModelMap mp) {
        return "categoryForm";
    }

    @PostMapping(path="")
    public String addCategory(Category newCategory, ModelMap mp) {
        Category category = categoryRepository.findByName(newCategory.getName());

        if(category == null) { // TODO : gestion de l'erreur
            //return an error
            categoryRepository.save(newCategory);
        }

        mp.addAttribute("categories", categoryRepository.findAll());
        mp.addAttribute("carpets", carpetRepository.findAll());
        return "admin";
    }

    @GetMapping(path="/delete")
    public String deleteCategory(ModelMap mp, @RequestParam("name") String name) {
        Category category = categoryRepository.findByName(name);

        if(!category.equals("")) {
            //return an error
        }

        return "";
    }
}
