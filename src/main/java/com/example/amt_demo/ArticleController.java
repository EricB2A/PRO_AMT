package com.example.amt_demo;

import com.example.amt_demo.model.*;
import com.example.amt_demo.service.CarpetService;
import com.example.amt_demo.service.CategoryService;
import com.example.amt_demo.service.PhotoUploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    final private CarpetService carpetService;
    final private CategoryService categoryService;

    public ArticleController(PhotoUploadService photoStorageService, CarpetService carpetService, CategoryService categoryService) {
        this.photoStorageService = photoStorageService;
        this.carpetService = carpetService;
        this.categoryService = categoryService;
    }

    @GetMapping(path="", produces = {"application/xml"})
    public String getAllCarpets(ModelMap mp) {
        mp.addAttribute("articles", carpetService.findAll());
        return "admin/articles";
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable int id) {
        if(!carpetService.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        carpetService.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/add")
    public String addCarpetForm(ModelMap mp) {
        mp.addAttribute("categories_not_checked", categoryService.getAllCategories());
        mp.addAttribute("adding", true);
        mp.addAttribute("post_url", "/admin/articles/add/post");
        return "admin/articleForm";
    }

    @PostMapping("/add/post")
    public RedirectView addCarpet(Carpet newCarpet, @RequestParam(name = "categories", required = false) String categories, @RequestParam(name = "images", required = false) MultipartFile[] images, RedirectAttributes redir, ModelMap mp) {
        // TODO : Image upload
        if(newCarpet.getName().length() == 0){
            RedirectView redirectView = new RedirectView("/admin/articles/add",true);
            redir.addFlashAttribute("msg_missing_name",true);
            return redirectView;
        }

        Carpet tryFind = carpetService.findByName(newCarpet.getName());
        if(tryFind != null){
            RedirectView redirectView = new RedirectView("/admin/articles/add",true);
            redir.addFlashAttribute("msg_already_existing_article",true);
            return redirectView;
        }
        carpetService.save(newCarpet);
        this.uploadImages(newCarpet, images);
        carpetService.save(newCarpet);
        if(categories != null) {
            for (String c : categories.split(",")) {
                categoryService.addCategoryToCarpet(Integer.valueOf(newCarpet.getId()), Integer.valueOf(c));
            }
        }
        RedirectView redirectView = new RedirectView("/admin/articles",true);
        redir.addFlashAttribute("msg_article_added",true);
        return redirectView;
    }

    @GetMapping("{carpet_id}/photo/delete/{id}")
    public RedirectView deleteCarpetPhoto(ModelMap mp, @PathVariable String carpet_id, @PathVariable String id, RedirectAttributes redir) {
        Optional<Carpet> carpet = carpetService.findById(Integer.parseInt(carpet_id));
        if(carpet.isPresent()) {
            Carpet c = carpet.get();
            String path;
            for(CarpetPhoto cp : c.getPhotos()){
                if(cp.getId() == Integer.parseInt(id)){
                    path = cp.getPath();
                    c.getPhotos().remove(cp);
                    photoStorageService.delete(path);
                    break;
                }
            }
            carpetService.save(c);

        }
        RedirectView redirectView = new RedirectView("/admin/articles/edit/"+carpet_id,true);
        redir.addFlashAttribute("msg_photo_deleted",true);
        return redirectView;
    }

    private void handleQuantity(Integer carpetId, Integer toAdd){
        Carpet carpet = carpetService.findId(carpetId);
        Integer current = carpet.getQuantity();
        // TODO What's best ?
        //Integer current = carpet.getQuantity() != null ? carpet.getQuantity() : 0;
        if(current + toAdd >= 0){
            carpet.setQuantity(carpet.getQuantity()+toAdd);
            carpetService.save(carpet);
        }
    }

    @GetMapping("/quantity/increase/{id}")
    public RedirectView increaseQuantity(ModelMap mp, @PathVariable String id, RedirectAttributes redir) {
        this.handleQuantity(Integer.parseInt(id), 1);
        RedirectView redirectView = new RedirectView("/admin/articles",true);
        redir.addFlashAttribute("msg_article_quantity_increase",true);
        redir.addFlashAttribute("articles",carpetService.findAll());
        return redirectView;
    }

    @GetMapping("/quantity/decrease/{id}")
    public RedirectView decreaseQuantity(ModelMap mp, @PathVariable String id, RedirectAttributes redir) {
        this.handleQuantity(Integer.parseInt(id), -1);
        RedirectView redirectView = new RedirectView("/admin/articles",true);
        redir.addFlashAttribute("msg_article_quantity_decrease",true);
        redir.addFlashAttribute("articles", carpetService.findAll());
        return redirectView;
    }

    @PostMapping("/edit/post")
    public RedirectView editArticle(Carpet updated, @RequestParam(name = "categories", required = false) String categories, @RequestParam(name = "images", required = false) MultipartFile[] images, RedirectAttributes redir) {

        this.uploadImages(updated, images);
        carpetService.save(updated);
        if(categories != null) {

            //TODO Ici ça déconne
            for (String c : categories.split(",")) {
                categoryService.addCategoryToCarpet(Integer.valueOf(updated.getId()), Integer.valueOf(c));
            }
        }
        RedirectView redirectView = new RedirectView("/admin/articles/edit/"+updated.getId(),true);
        redir.addFlashAttribute("msg_article_edited",true);
        return redirectView;
    }

    @GetMapping("/edit/{id}")
    public String editArticle(ModelMap mp, @PathVariable String id) {
        mp.addAttribute("editing", true);
        mp.addAttribute("post_url", "/admin/articles/edit/post");
        mp.addAttribute("article", carpetService.findById(Integer.valueOf(id)));
        List<Category> checked = categoryService.getCategoriesOfCarpet(Integer.valueOf(id));
        List<Category> notChecked = categoryService.getAllCategories();
        for(Category cat : checked) notChecked.remove(cat);
        mp.addAttribute("categories_checked", checked);
        mp.addAttribute("categories_not_checked", notChecked);
        return "admin/articleForm";
    }

    @GetMapping("/delete/{id}")
    public String deleteCarpet(ModelMap mp, @PathVariable String id) {
        Carpet carpet = carpetService.findId(Integer.parseInt(id));

        carpetService.delete(carpet);

        mp.addAttribute("categories", categoryService.findAll());
        mp.addAttribute("articles", carpetService.findAll());
        mp.addAttribute("msg_article_deleted", true);
        return "admin/articles";
    }

    private void uploadImages(Carpet carpet, MultipartFile[] images){
        AtomicInteger i = new AtomicInteger(1);
        Arrays.asList(images).stream().forEach(image -> {
            String filename = image.getOriginalFilename();
            if(filename.length() > 4){
                String ext = filename.substring(filename.lastIndexOf(".") + 1);
                String location = "/carpet"+carpet.getId()+"/";
                String name = "carpet"+ i.getAndIncrement() +"."+ext;
                photoStorageService.save(image, location, name);
                carpet.getPhotos().add(new CarpetPhoto(photoStorageService.getRoot() + location + name));
            }
        });
    }


}
