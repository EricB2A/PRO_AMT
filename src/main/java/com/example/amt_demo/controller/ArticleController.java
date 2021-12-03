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
import com.example.amt_demo.service.PhotoUploadService;
import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@RequestMapping(path = "/admin/articles")
@Controller
@AllArgsConstructor
// DPE - Du moment que vous avez des services autant cacher la logique dans ces services. Et que le controller appel le service.
public class ArticleController {

    final private PhotoUploadService photoStorageService;
    final private ArticleService articleService;
    final private CategoryService categoryService;

    // DPE - Vous connaissez lombok ? (@AllArgsConstructor)
    // TODO - DONE

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
        RedirectView redirectView = checkArticleInputs(newArticle, redir, "/admin/articles/add");
        if (redirectView != null) return redirectView;

        Article tryFind = articleService.findByName(newArticle.getName());
        if(tryFind != null){
            redirectView = new RedirectView("/admin/articles/add",true);
            redir.addFlashAttribute("msg_already_existing_article",true);
            return redirectView;
        }

        articleService.save(newArticle);
        this.uploadImages(newArticle, images);
        articleService.save(newArticle);

        redirectView = new RedirectView("/admin/articles",true);
        redir.addFlashAttribute("msg_article_added",true);
        return redirectView;
    }

    /**
     *
     * @param carpet_id
     * @param id
     * @param redir
     * @return
     */
    @GetMapping("{carpet_id}/photo/delete/{id}")
    public RedirectView deleteCarpetPhoto(@PathVariable Long carpet_id, @PathVariable String id, RedirectAttributes redir) {
        Article carpet = articleService.findById(carpet_id);

        if(carpet != null) {
            String path;
            for(ArticlePhoto cp : carpet.getPhotos()){
                if(cp.getId() == Integer.parseInt(id)){
                    path = cp.getPath();
                    carpet.getPhotos().remove(cp);
                    photoStorageService.delete(path);
                    break;
                }
            }
            articleService.save(carpet);
        }
        RedirectView redirectView = new RedirectView("/admin/articles/edit/"+carpet_id,true);
        redir.addFlashAttribute("msg_photo_deleted",true);
        return redirectView;
    }

    /**
     *
     * @param carpetId
     * @param toAdd
     */
    private boolean handleQuantity(Long carpetId, Integer toAdd){

        // DPE - Si tu mets cette logique avec l'optional dans le service tu peux gérer que l'objet existe pas avec des exceptions
        // TODO - DONE, géré l'optional dans Service
        Article article = articleService.findById(carpetId);

        // DPE - https://dev.to/jpswade/return-early-12o5
        // TODO - DONE
        if(article == null) {
            return false;
        }

        Integer current = article.getQuantity();
        if(current + toAdd >= 0){
            article.setQuantity(article.getQuantity() + toAdd);
            articleService.save(article);
            return true;
        }
        return false;
    }

    /**
     *
     * @param id
     * @param redir
     * @return
     */
    @GetMapping("/quantity/increase/{id}")
    // DPE - les variables pas utilisés
    // TODO - DONE, removed
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
        String url = "/admin/articles/edit/" + updated.getId();
        Article tryFind = articleService.findByName(updated.getName());
        if(tryFind != null && !tryFind.getId().equals(updated.getId())){
            RedirectView redirectView = new RedirectView(url,true);
            redir.addFlashAttribute("msg_already_existing_article",true);
            return redirectView;
        }
        RedirectView redirectView1 = checkArticleInputs(updated, redir, url);
        if (redirectView1 != null) return redirectView1;
        Article article = articleService.findById(updated.getId());
        if(article != null) {
            updated.setPhotos(article.getPhotos());
        }
        this.uploadImages(updated, images);
        articleService.save(updated);

        RedirectView redirectView = new RedirectView("/admin/articles/edit/" + updated.getId(),true);
        redir.addFlashAttribute("msg_article_edited",true);
        return redirectView;
    }

    /**
     *
     * @param mp
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    // DPE - Tu ne peux pas dire que ton paramètre est directement un Long ?
    public String editArticle(ModelMap mp, @PathVariable Long id) {
        mp.addAttribute("editing", true);
        mp.addAttribute("post_url", "/admin/articles/edit/post");
        Article article = articleService.findById(id);
        if(article != null) {
            mp.addAttribute("article", article);
        }

        // DPE - Ne peux tu pas faire une seul map qui connait si la catégorie est checkée ou pas ?
        List<Category> checked = categoryService.getCategoriesOfCarpet(id);
        List<Category> notChecked = categoryService.getAllCategories();

        for(Category cat : checked) {
            notChecked.remove(cat);
        }
        mp.addAttribute("categories_checked", checked);
        mp.addAttribute("categories_not_checked", notChecked);
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
        Article article = articleService.findById(id);
        if(article != null){
            articleService.delete(article);
            photoStorageService.deleteFolder("carpet-photos/carpet"+ article.getId());
            mp.addAttribute("msg_article_deleted", true);
        }
        mp.addAttribute("articles", articleService.findAll());
        return "admin/articles";
    }

    @Nullable
    private RedirectView checkArticleInputs(Article updated, RedirectAttributes redir, String redirTo) {
        if(updated.getName().length() == 0){
            RedirectView redirectView = new RedirectView(redirTo,true);
            redir.addFlashAttribute("msg_missing_name",true);
            return redirectView;
        }

        if(updated.getPrice() == null || updated.getPrice() < 0) updated.setPrice(0.0);
        if(updated.getQuantity() == null || updated.getQuantity() < 0) updated.setQuantity(0);
        return null;
    }

    /**
     * @param article
     * @param images
     * @return
     */
    private boolean uploadImages(Article article, MultipartFile[] images){
        AtomicInteger i = new AtomicInteger(1);
        if(images != null) {
            Arrays.asList(images).stream().forEach(image -> {
                String filename = image.getOriginalFilename();
                if (filename.length() > 4) {
                    String location = "/carpet" + article.getId() + "/";
                    if (photoStorageService.save(image, location, filename)) {
                        article.getPhotos().add(new ArticlePhoto(photoStorageService.getRoot() + location + filename));
                        i.incrementAndGet();
                    }
                }
            });
        }
        return i.get() > 1;
    }
}