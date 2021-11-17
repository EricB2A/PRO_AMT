package com.example.amt_demo;

import com.example.amt_demo.model.CarpetRepository;
import com.example.amt_demo.model.Category;
import com.example.amt_demo.model.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/admin/categories")
@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CarpetRepository carpetRepository;

    @GetMapping(path="", produces = {"application/xml"})
    public String getAllCarpets(ModelMap mp) {
        mp.addAttribute("categories", categoryRepository.findAll());
        return "admin/categories";
    }

    @GetMapping("/add")
    public String getCategoryForm(ModelMap mp) {
        return "admin/categoryForm";
    }

    @PostMapping(path="/add/post")
    public String addCategory(Category newCategory, ModelMap mp) {
        Category category = categoryRepository.findByName(newCategory.getName());

        if(category == null) {
            categoryRepository.save(newCategory);
        }else {
            mp.addAttribute("error", "Cette catégorie existe déjà");
        }
        mp.addAttribute("categories", categoryRepository.findAll());
        return "admin/categories";
    }

    @PostMapping(path="/add/{id}")
    public String addCategoriesToCarpet(@PathVariable String id, @RequestParam String categories, ModelMap mp) {

        for(String c : categories.split(",")) {
            categoryRepository.addCategoryToCarpet(Integer.valueOf(id), Integer.valueOf(c));
        }

        mp.addAttribute("categories", categoryRepository.findAll());
        mp.addAttribute("articles", carpetRepository.findAll());
        return "admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(ModelMap mp, @PathVariable String id) {
        mp.addAttribute("category", categoryRepository.findById(Integer.valueOf(id)));
        return "admin/categories";
    }


    @GetMapping("/delete/{id}")
    public String deleteCategory(ModelMap mp, @PathVariable String id) {
        Category category = categoryRepository.findId(Integer.valueOf(id));
        if(category != null) {
            List<Category> list = categoryRepository.hasArticlesInCategory(Integer.valueOf(id));

            if (list.isEmpty()) {
                categoryRepository.delete(category);
            } else {
                mp.addAttribute("error", "Vous ne pouvez pas supprimer des catégories qui contiennent des articles");
                mp.addAttribute("error_article", categoryRepository.findErrorDeletion(Integer.valueOf(id)));
            }
        }
        mp.addAttribute("categories", categoryRepository.findAll());

        return "admin/categories";
    }



}
