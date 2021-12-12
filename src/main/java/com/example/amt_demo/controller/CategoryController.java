/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CategoryController.java
 *
 * @brief Controller of the Category object
 */

package com.example.amt_demo.controller;

import com.example.amt_demo.model.ArticleRepository;
import com.example.amt_demo.model.Category;
import com.example.amt_demo.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping(path = "/admin/categories")
@Controller
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ArticleRepository articleRepository;

    /**
     * Method getting all carpets in the database
     * @param mp a Modelmap
     * @return the view in the admin section in order to display the list of carpets
     */
    @GetMapping(path="", produces = {"application/xml"})
    public String getAllCarpets(ModelMap mp) {
        mp.addAttribute("categories", categoryService.findAll());
        return "admin/categories";
    }

    /**
     * Method displaying the form for adding a category
     * @return the view in the admin section in order to add a new category
     */
    @GetMapping("/add")
    public String getCategoryForm(ModelMap mp) {
        mp.addAttribute("adding", true);
        mp.addAttribute("post_url", "/admin/categories/add/post");
        return "admin/categoryForm";
    }

    /**
     * Method adding the Category to the database
     * @param newCategory the Category to add
     * @param mp the ModelMap
     * @return the view to the list of Category
     */
    @PostMapping(path="/add/post")
    public String addCategory(Category newCategory, ModelMap mp) {
        Category category = categoryService.findByName(newCategory.getName());

        if(category == null) {
            categoryService.save(newCategory);
        } else {
            mp.addAttribute("error", "Cette catégorie existe déjà");
        }
        mp.addAttribute("categories", categoryService.findAll());
        return "admin/categories";
    }

    /**
     * Method displaying the form for editing a category
     * @param mp the ModelMap
     * @param id the id of the Category to edit
     * @return the view to the edition form
     */
    @GetMapping("/edit/{id}")
    public String editCategory(ModelMap mp, @PathVariable String id) {
        mp.addAttribute("category", categoryService.findById(Integer.parseInt(id)));
        mp.addAttribute("editing", true);
        mp.addAttribute("post_url", "/admin/categories/edit/post");
        return "admin/categoryForm";
    }

    /**
     * Method editing the category to the database
     * @param updated the updated Category
     * @param mp the ModelMap
     * @return the view to the list of Category
     */
    @PostMapping("/edit/post")
    public String editArticle(Category updated, ModelMap mp) {
        Category category = categoryService.findByName(updated.getName());

        if(category == null) {
            categoryService.save(updated);
            mp.addAttribute("success","Nom de la catégorie modifiée");
        } else {
            mp.addAttribute("error","Une catégorie portant ce nom existe déjà");
        }
        mp.addAttribute("categories", categoryService.findAll());
        return "admin/categories";
    }

    /**
     * Method deleting the category of the database
     * @param mp the ModelMap
     * @param id the id of the Category to delete
     * @return the view to the list of Category
     */
    @GetMapping("/delete/{id}")
    public String deleteCategory(ModelMap mp, @PathVariable Integer id) {
        Category category = categoryService.findById(id);
        Set<Category> list = categoryService.hasArticlesInCategory(id);
        if (list.isEmpty()) { // Checking if there is articles linked to the Category
            categoryService.delete(category);
            mp.addAttribute("success", "Catégorie supprimée");
        } else { // Otherwise, send error message & list of Article objects preventing the deletion
            mp.addAttribute("error", "Vous ne pouvez pas supprimer des catégories qui contiennent des articles");
            mp.addAttribute("error_article", articleRepository.findErrorDeletion(id));
        }

        mp.addAttribute("categories", categoryService.findAll());
        return "admin/categories";
    }
}