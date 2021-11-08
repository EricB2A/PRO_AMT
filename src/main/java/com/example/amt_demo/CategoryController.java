package com.example.amt_demo;

import com.example.amt_demo.model.CarpetRepository;
import com.example.amt_demo.model.Category;
import com.example.amt_demo.model.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping(path="")
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


    @GetMapping("/category/edit/{id}")
    public String editCategory(ModelMap mp, @PathVariable String id) {
        System.out.println(id);
        mp.addAttribute("category", categoryRepository.findById(Integer.valueOf(id)));
        return "admin/category";
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(ModelMap mp, @PathVariable String id) {
        Category category = categoryRepository.findId(Integer.valueOf(id));
        //Category category1 = categoryRepository.findById(Integer.valueOf(id));

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
}
