/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file ArticleService.java
 *
 * @brief Service of Article
 */

package com.example.amt_demo.service;

import com.example.amt_demo.model.*;

import com.example.amt_demo.service.photo.LocalPhotoUploadServiceImpl;
import com.example.amt_demo.service.photo.PhotoUploadService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ArticleService {

    final private ArticleRepository articleRepository;
    final private CategoryRepository categoryRepository;

    @Qualifier("amazonService")
    @Autowired
    private PhotoUploadService photoService;

    /**
     *
     * @return
     */
    public Iterable<Article> findAll() {
        return articleRepository.findAll();
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

    public RedirectView addCarpet(Article newArticle, MultipartFile[] images, RedirectAttributes redir) {
        RedirectView redirectView = checkArticleInputs(newArticle, redir, "/admin/articles/add");
        if (redirectView != null) return redirectView;

        Article tryFind = articleRepository.findByName(newArticle.getName());
        if(tryFind != null){
            redirectView = new RedirectView("/admin/articles/add",true);
            redir.addFlashAttribute("msg_already_existing_article",true);
            return redirectView;
        }
        articleRepository.save(newArticle);
        this.uploadImages(newArticle, images);
        articleRepository.save(newArticle);

        redirectView = new RedirectView("/admin/articles",true);
        redir.addFlashAttribute("msg_article_added",true);
        return redirectView;
    }

    public RedirectView deleteCarpetPhoto(Long carpet_id, Integer id, RedirectAttributes redir) {
        Optional<Article> carpet = articleRepository.findById(carpet_id);
        if(carpet.isPresent()) {
            Article c = carpet.get();
            String path;
            for(ArticlePhoto cp : c.getPhotos()){
                if(cp.getId() == id){
                    path = cp.getPath();
                    c.getPhotos().remove(cp);
                    photoService.delete(path);
                    break;
                }
            }
            articleRepository.save(c);
        }
        RedirectView redirectView = new RedirectView("/admin/articles/edit/"+carpet_id,true);
        redir.addFlashAttribute("msg_photo_deleted",true);
        return redirectView;

    }

    public boolean handleQuantity(Long carpetId, Integer toAdd){
        Optional<Article> optional = articleRepository.findById(carpetId);

        if(optional.isPresent()){
            Article article = optional.get();
            Integer current = article.getQuantity();
            if(current + toAdd >= 0){
                article.setQuantity(article.getQuantity()+toAdd);
                articleRepository.save(article);
                return true;
            }
        }
        return false;
    }

    public RedirectView editArticle(Article updated, MultipartFile[] images, RedirectAttributes redir) {
        String url = "/admin/articles/edit/"+updated.getId();
        Article tryFind = articleRepository.findByName(updated.getName());
        if(tryFind != null && !tryFind.getId().equals(updated.getId())){
            RedirectView redirectView = new RedirectView(url,true);
            redir.addFlashAttribute("msg_already_existing_article",true);
            return redirectView;
        }
        RedirectView redirectView1 = checkArticleInputs(updated, redir, url);
        if (redirectView1 != null) return redirectView1;
        Optional<Article> optional = articleRepository.findById(updated.getId());
        optional.ifPresent(article -> updated.setPhotos(article.getPhotos()));
        this.uploadImages(updated, images);
        articleRepository.save(updated);

        RedirectView redirectView = new RedirectView("/admin/articles/edit/" + updated.getId(),true);
        redir.addFlashAttribute("msg_article_edited",true);
        return redirectView;
    }

    public void editArticleForm(ModelMap mp, Long id) {
        mp.addAttribute("editing", true);
        mp.addAttribute("post_url", "/admin/articles/edit/post");
        Optional<Article> optional = articleRepository.findById(id);
        optional.ifPresent(article -> mp.addAttribute("article", article));
        HashMap<Category, Boolean> categories = new HashMap<>();
        List<Category> checked = categoryRepository.getCategoriesOfArticle(id);
        for(Category cat : categoryRepository.getAllCategories()) {
            categories.put(cat, checked.contains(cat));
        }
        mp.addAttribute("categories", categories);
    }

    public void deleteCarpet(ModelMap mp, Long id) {
        Optional<Article> optional = articleRepository.findById(id);
        if(optional.isPresent()){
            Article article = optional.get();
            articleRepository.delete(article);
            photoService.deleteFolder("carpet-photos/carpet"+ article.getId());
            mp.addAttribute("msg_article_deleted", true);
        }
        mp.addAttribute("articles", articleRepository.findAll());
    }

    /**
     *  @param article
     * @param images
     * @return
     */
    private boolean uploadImages(Article article, MultipartFile[] images){
        AtomicInteger i = new AtomicInteger(1);
        if(images != null) {
            Arrays.asList(images).stream().forEach(image -> {
                String filename = image.getOriginalFilename();
                if (filename.length() > 4) {
                    String location = "carpet" + article.getId() + "/";
                    if (photoService.save(image, location, filename)) {
                        article.getPhotos().add(new ArticlePhoto(location + filename));
                        i.incrementAndGet();
                    }
                }
            });
        }
        return i.get() > 1;
    }

}