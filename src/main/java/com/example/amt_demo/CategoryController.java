package com.example.amt_demo;

import com.example.amt_demo.model.Carpet;
import com.example.amt_demo.model.CarpetRepository;
import com.example.amt_demo.model.Category;
import com.example.amt_demo.model.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
        return "admin/admin";
    }

    @GetMapping("/category/new")
    public String getCategoryForm(ModelMap mp) {
        return "admin/categoryForm";
    }

    @PostMapping(path="/add")
    public String addCategory(Category newCategory, ModelMap mp) {
        Category category = categoryRepository.findByName(newCategory.getName());

        if(category == null) {
            categoryRepository.save(newCategory);
        }else {
            mp.addAttribute("error", "Cette catégorie existe déjà");
        }

        mp.addAttribute("categories", categoryRepository.findAll());
        mp.addAttribute("carpets", carpetRepository.findAll());
        return "admin/admin";
    }

    @PostMapping(path="/addCategory/{id}")
    public String addCategoriesToCarpet(@PathVariable String id, @RequestParam String categories, ModelMap mp) {

        for(String c : categories.split(",")) {
            categoryRepository.addCategoryToCarpet(Integer.valueOf(id), Integer.valueOf(c));
        }

        mp.addAttribute("categories", categoryRepository.findAll());
       mp.addAttribute("carpets", carpetRepository.findAll());
        return "admin/admin";
    }

    @GetMapping("/category/edit/{id}")
    public String editCategory(ModelMap mp, @PathVariable String id) {
        mp.addAttribute("category", categoryRepository.findById(Integer.valueOf(id)));
        return "admin/category";
    }

    @GetMapping("/carpet/edit/{id}")
    public String editArticle(ModelMap mp, @PathVariable String id) {
        mp.addAttribute("article", carpetRepository.findById(Integer.valueOf(id)));
        mp.addAttribute("categories", categoryRepository.findCategoryNotBelongingToCarpet(Integer.valueOf(id)));
        return "admin/article";
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(ModelMap mp, @PathVariable String id) {
        Category category = categoryRepository.findId(Integer.valueOf(id));

        List<Category> list = categoryRepository.hasArticlesInCategory(Integer.valueOf(id));

        if(list.isEmpty()) {
            categoryRepository.delete(category);
        } else {
            mp.addAttribute("error", "Vous ne pouvez pas supprimer des catégories qui contiennent des articles");
        }

        mp.addAttribute("categories", categoryRepository.findAll());
        mp.addAttribute("carpets", carpetRepository.findAll());

        return "admin/admin";
    }


    @GetMapping("/carpet/delete/{id}")
    public String deleteCarpet(ModelMap mp, @PathVariable String id) {
        Carpet carpet = carpetRepository.findId(Integer.valueOf(id));

        carpetRepository.delete(carpet);

        mp.addAttribute("categories", categoryRepository.findAll());
        mp.addAttribute("carpets", carpetRepository.findAll());

        return "admin/admin";
    }
}
