/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file ArticleController.java
 *
 * @brief TODO
 */

package com.example.amt_demo.controller;

import com.example.amt_demo.model.*;
import com.example.amt_demo.service.ArticleService;
import com.example.amt_demo.service.CategoryService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RequestMapping(path = "/admin/articles")
@Controller
@AllArgsConstructor
public class ArticleController {

    final private ArticleService articleService;
    final private CategoryService categoryService;


    /**
     *
     * @param mp
     * @return
     */

    @GetMapping(path="", produces = {"application/xml"})
    public String getAllCarpets(ModelMap mp) {
        mp.addAttribute("articles", articleService.findAll());
        return "admin/articles";
    }

    /**
     *
     * @param mp
     * @return
     */
    @GetMapping("/add")
    public String addCarpetForm(ModelMap mp) {
        mp.addAttribute("categories_not_checked", categoryService.getAllCategories());
        mp.addAttribute("adding", true);
        mp.addAttribute("post_url", "/admin/articles/add/post");
        return "admin/articleForm";
    }

    /**
     *
     * @param newArticle
     * @param images
     * @param redir
     * @return
     */
    @PostMapping("/add/post")
    public RedirectView addCarpet(Article newArticle, @RequestParam(name = "images", required = false) MultipartFile[] images, RedirectAttributes redir) {

       return articleService.addCarpet(newArticle, images, redir);

    }

    /**
     *
     * @param carpet_id
     * @param id
     * @param redir
     * @return
     */
    @GetMapping("{carpet_id}/photo/delete/{id}")

    public RedirectView deleteCarpetPhoto(@PathVariable Long carpet_id, @PathVariable Integer id, RedirectAttributes redir) {
        return articleService.deleteCarpetPhoto(carpet_id, id, redir);
    }

    /**
     *
     * @param carpetId
     * @param toAdd
     */
    private boolean handleQuantity(Long carpetId, Integer toAdd){
        return articleService.handleQuantity(carpetId, toAdd);
    }

    /**
     *
     * @param id
     * @param redir
     * @return
     */
    @GetMapping("/quantity/increase/{id}")
    // DPE - les variables pas utilisés
    public RedirectView increaseQuantity(@PathVariable Long id, RedirectAttributes redir) {
        this.handleQuantity(id, 1);
        redir.addFlashAttribute("msg_article_quantity_increase",true);
        RedirectView redirectView = new RedirectView("/admin/articles",true);
        redir.addFlashAttribute("articles", articleService.findAll());
        return redirectView;
    }

    /**
     *
     * @param id
     * @param redir
     * @return
     */
    @GetMapping("/quantity/decrease/{id}")
    public RedirectView decreaseQuantity(@PathVariable Long id, RedirectAttributes redir) {
        if(this.handleQuantity(id, -1)){
            redir.addFlashAttribute("msg_article_quantity_decrease",true);
        }
        RedirectView redirectView = new RedirectView("/admin/articles",true);
        redir.addFlashAttribute("articles", articleService.findAll());
        return redirectView;
    }

    /**
     *
     * @param updated
     * @param images
     * @param redir
     * @return
     */
    @PostMapping("/edit/post")
    public RedirectView editArticle(Article updated, @RequestParam(name = "images", required = false) MultipartFile[] images, RedirectAttributes redir) {
        return articleService.editArticle(updated, images, redir);
    }

    /**
     *
     * @param mp
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public String editArticle(ModelMap mp, @PathVariable Long id) {
        articleService.editArticleForm(mp, id);
        return "admin/articleForm";
    }

    /**
     *
     * @param mp
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public String deleteCarpet(ModelMap mp, @PathVariable Long id) {
        articleService.deleteCarpet(mp, id);
        return "admin/articles";
    }


}