/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CatalogController.java
 *
 * @brief Controller used for the Catalog
 */

package com.example.amt_demo.controller;

import com.example.amt_demo.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RequestMapping(path = "/catalog")
@Controller
public class CatalogController {

    private ArticleRepository articleRepository;
    private CategoryRepository categoryRepository;

    /**
     * Constructor of the CatalogController
     * @param articleRepository
     * @param categoryRepository
     */
    public CatalogController(ArticleRepository articleRepository, CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Method getting every article of the database and every category associated with
     * an article, used as filters
     * @param mp a ModelMap
     * @return the view to the catalog
     */
    @GetMapping(path="", produces = {"application/xml"})
    public String getAllArticles(ModelMap mp) {
        mp.addAttribute("articles", articleRepository.findAll());
        mp.addAttribute("categories", categoryRepository.notEmptyCategory());
        return "public/catalog";
    }

    /**
     * Method getting the selected article and providing informations
     * @param mp a ModelMap
     * @param id the id of the chosen article
     * @return the view to the article
     */
    @GetMapping(path="/{id}", produces = {"application/xml"})
    public String getArticle(ModelMap mp, @PathVariable String id) {
        Optional<Article> optional = articleRepository.findById(Long.valueOf(id));
        optional.ifPresent(article -> mp.addAttribute("article", article));
        return "public/article";
    }

    /**
     * Method returning a list of articles based on the selected filter
     * @param mp a ModelMap
     * @param name the name of the chosen category
     * @return the view to the catalog with a filter applied
     */
    @GetMapping(path="/filter/{name}", produces = {"application/xml"})
    public String getArticlesFilter(ModelMap mp, @PathVariable String name) {
        mp.addAttribute("articles", articleRepository.findByFilter(name));
        mp.addAttribute("categories", categoryRepository.notEmptyCategory());
        mp.addAttribute("filter", name);
        return "public/catalog";
    }
}