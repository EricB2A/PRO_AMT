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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


@RequestMapping(path = "/admin/articles")
@Controller
public class ArticleController {

    final private PhotoUploadService photoStorageService;
    final private ArticleService articleService;
    final private CategoryService categoryService;

    /**
     *
     * @param photoStorageService
     * @param articleService
     * @param categoryService
     */
    public ArticleController(PhotoUploadService photoStorageService, ArticleService articleService, CategoryService categoryService) {
        this.photoStorageService = photoStorageService;
        this.articleService = articleService;
        this.categoryService = categoryService;
    }

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
        if(newArticle.getName().length() == 0){
            RedirectView redirectView = new RedirectView("/admin/articles/add",true);
            redir.addFlashAttribute("msg_missing_name",true);
            return redirectView;
        }
        if(newArticle.getPrice() == null || newArticle.getPrice() < 0) newArticle.setPrice(0.0);
        if(newArticle.getQuantity() == null || newArticle.getQuantity() < 0) newArticle.setQuantity(0);

        Article tryFind = articleService.findByName(newArticle.getName());
        if(tryFind != null){
            RedirectView redirectView = new RedirectView("/admin/articles/add",true);
            redir.addFlashAttribute("msg_already_existing_article",true);
            return redirectView;
        }
        articleService.save(newArticle);
        this.uploadImages(newArticle, images);
        articleService.save(newArticle);

        RedirectView redirectView = new RedirectView("/admin/articles",true);
        redir.addFlashAttribute("msg_article_added",true);
        return redirectView;
    }

    /**
     *
     * @param mp
     * @param carpet_id
     * @param id
     * @param redir
     * @return
     */
    @GetMapping("{carpet_id}/photo/delete/{id}")
    public RedirectView deleteCarpetPhoto(ModelMap mp, @PathVariable String carpet_id, @PathVariable String id, RedirectAttributes redir) {
        Optional<Article> carpet = articleService.findById(Integer.parseInt(carpet_id));
        if(carpet.isPresent()) {
            Article c = carpet.get();
            String path;
            for(ArticlePhoto cp : c.getPhotos()){
                if(cp.getId() == Integer.parseInt(id)){
                    path = cp.getPath();
                    c.getPhotos().remove(cp);
                    photoStorageService.delete(path);
                    break;
                }
            }
            articleService.save(c);
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
    private boolean handleQuantity(Integer carpetId, Integer toAdd){
        Optional<Article> optional = articleService.findById(carpetId);
        if(optional.isPresent()){
            Article article = optional.get();
            Integer current = article.getQuantity();
            if(current + toAdd >= 0){
                article.setQuantity(article.getQuantity()+toAdd);
                articleService.save(article);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param mp
     * @param id
     * @param redir
     * @return
     */
    @GetMapping("/quantity/increase/{id}")
    public RedirectView increaseQuantity(ModelMap mp, @PathVariable String id, RedirectAttributes redir) {
        this.handleQuantity(Integer.parseInt(id), 1);
        RedirectView redirectView = new RedirectView("/admin/articles",true);
        redir.addFlashAttribute("msg_article_quantity_increase",true);
        redir.addFlashAttribute("articles", articleService.findAll());
        return redirectView;
    }

    /**
     *
     * @param mp
     * @param id
     * @param redir
     * @return
     */
    @GetMapping("/quantity/decrease/{id}")
    public RedirectView decreaseQuantity(ModelMap mp, @PathVariable String id, RedirectAttributes redir) {
        if(this.handleQuantity(Integer.parseInt(id), -1)){
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
        if(updated.getPrice() == null || updated.getPrice() < 0) updated.setPrice(0.0);
        if(updated.getQuantity() == null || updated.getQuantity() < 0) updated.setQuantity(0);

        if(!this.uploadImages(updated, images)) {
            Optional<Article> optional = articleService.findById(updated.getId());
            if(optional.isPresent()) {
                updated.setPhotos(optional.get().getPhotos());
            }
        }
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
    public String editArticle(ModelMap mp, @PathVariable String id) {
        mp.addAttribute("editing", true);
        mp.addAttribute("post_url", "/admin/articles/edit/post");
        mp.addAttribute("article", articleService.findById(Integer.valueOf(id)));

        List<Category> checked = categoryService.getCategoriesOfCarpet(Integer.valueOf(id));
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
    public String deleteCarpet(ModelMap mp, @PathVariable String id) {
        Optional<Article> article = articleService.findById(Integer.parseInt(id));

        articleService.delete(article.get());

        mp.addAttribute("categories", categoryService.findAll());
        mp.addAttribute("articles", articleService.findAll());
        mp.addAttribute("msg_article_deleted", true);

        return "admin/articles";
    }

    /**
     *  @param article
     * @param images
     * @return
     */
    private boolean uploadImages(Article article, MultipartFile[] images){
        AtomicInteger i = new AtomicInteger(1);
        Arrays.asList(images).stream().forEach(image -> {
            String filename = image.getOriginalFilename();
            if(filename.length() > 4){
                String ext = filename.substring(filename.lastIndexOf(".") + 1);
                String location = "/carpet"+ article.getId()+"/";
                photoStorageService.save(image, location, filename);
                article.getPhotos().add(new ArticlePhoto(photoStorageService.getRoot() + location + filename));
            }
        });
        return i.get() > 1;
    }
}