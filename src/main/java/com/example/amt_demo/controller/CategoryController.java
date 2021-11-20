/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CategoryController.java
 *
 * @brief Controller of the Category object
 */

package com.example.amt_demo.controller;

import com.example.amt_demo.model.Category;
import com.example.amt_demo.service.ArticleService;
import com.example.amt_demo.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping(path = "/admin/categories")
@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final ArticleService articleService;

    /**
     * Controller of Category
     * @param categoryService
     * @param articleService
     */
    public CategoryController(CategoryService categoryService, ArticleService articleService) {
        this.categoryService = categoryService;
        this.articleService = articleService;
    }

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
     *
     * @param newCategory
     * @param mp
     * @return
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

    //TODO N'est pas demandé dans le cahier des charges d'éditer la catégorie, ça fonctionne mais on jarte ?

    /**
     *
     * @param mp
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public String editCategory(ModelMap mp, @PathVariable String id) {
        mp.addAttribute("category", categoryService.findId(Integer.parseInt(id)));
        mp.addAttribute("editing", true);
        mp.addAttribute("post_url", "/admin/categories/edit/post");
        return "admin/categoryForm";
    }

    //TODO Idem ici
    /**
     *
     * @param updated
     * @param mp
     * @return
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
     *
     * @param mp
     * @param id
     * @return
     */
    //TODO COMMENT QUE CELA MARCHE LE DELETEMAPPING
    @GetMapping("/delete/{id}")
    public String deleteCategory(ModelMap mp, @PathVariable String id) {
        Category category = categoryService.findId(Integer.valueOf(id));
        if(category != null) {
            Set<Category> list = categoryService.hasArticlesInCategory(Integer.valueOf(id));

            if (list.isEmpty()) {
                categoryService.delete(category);
                mp.addAttribute("success", "Catégorie supprimée");
            } else {
                mp.addAttribute("error", "Vous ne pouvez pas supprimer des catégories qui contiennent des articles");
                mp.addAttribute("error_article", articleService.findErrorDeletion(id));
            }
        }
        mp.addAttribute("categories", categoryService.findAll());
        return "admin/categories";
    }
}