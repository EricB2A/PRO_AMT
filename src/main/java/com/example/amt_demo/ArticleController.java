package com.example.amt_demo;

import com.example.amt_demo.model.*;
import com.example.amt_demo.service.PhotoUploadService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CarpetRepository carpetRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    PhotoUploadService photoStorageService;

    @GetMapping(path="", produces = {"application/xml"})
    public String getAllCarpets(ModelMap mp) {
        mp.addAttribute("articles", carpetRepository.findAll());
        return "admin/articles";
    }



    @GetMapping("/add")
    public String addCarpetForm(ModelMap mp) {
        mp.addAttribute("categories_not_checked", categoryRepository.getAllCategories());
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

        Carpet tryFind = carpetRepository.findByName(newCarpet.getName());
        if(tryFind != null){
            RedirectView redirectView = new RedirectView("/admin/articles/add",true);
            redir.addFlashAttribute("msg_already_existing_article",true);
            return redirectView;
        }
        carpetRepository.save(newCarpet);
        this.uploadImages(newCarpet, images);
        carpetRepository.save(newCarpet);
        if(categories != null) {
            for (String c : categories.split(",")) {
                categoryRepository.addCategoryToCarpet(Integer.valueOf(newCarpet.getId()), Integer.valueOf(c));
            }
        }
        RedirectView redirectView = new RedirectView("/admin/articles",true);
        redir.addFlashAttribute("msg_article_added",true);
        return redirectView;
    }

    @GetMapping("{carpet_id}/photo/delete/{id}")
    public RedirectView deleteCarpetPhoto(ModelMap mp, @PathVariable String carpet_id, @PathVariable String id, RedirectAttributes redir) {
        Optional<Carpet> carpet = carpetRepository.findById(Integer.parseInt(carpet_id));
        if(carpet.isPresent()) {
            Carpet c = carpet.get();
            String path = "";
            for(CarpetPhoto cp : c.getPhotos()){
                if(cp.getId() == Integer.parseInt(id)){
                    path = cp.getPath();
                    c.getPhotos().remove(cp);
                    photoStorageService.delete(path);
                    break;
                }
            }
            carpetRepository.save(c);

        }
        RedirectView redirectView = new RedirectView("/admin/articles/edit/"+carpet_id,true);
        redir.addFlashAttribute("msg_photo_deleted",true);
        return redirectView;
    }



    private void handleQuantity(Integer carpetId, Integer toAdd){
        Carpet carpet = carpetRepository.findId(carpetId);
        Integer current = carpet.getQuantity();
        if(current + toAdd >= 0){
            carpet.setQuantity(carpet.getQuantity()+toAdd);
            carpetRepository.save(carpet);
        }
    }

    @GetMapping("/quantity/increase/{id}")
    public RedirectView increaseQuantity(ModelMap mp, @PathVariable String id, RedirectAttributes redir) {
        this.handleQuantity(Integer.parseInt(id), 1);
        RedirectView redirectView = new RedirectView("/admin/articles",true);
        redir.addFlashAttribute("msg_article_quantity_increase",true);
        redir.addFlashAttribute("articles",carpetRepository.findAll());
        return redirectView;
    }

    @GetMapping("/quantity/decrease/{id}")
    public RedirectView decreaseQuantity(ModelMap mp, @PathVariable String id, RedirectAttributes redir) {
        this.handleQuantity(Integer.parseInt(id), -1);
        RedirectView redirectView = new RedirectView("/admin/articles",true);
        redir.addFlashAttribute("msg_article_quantity_decrease",true);
        redir.addFlashAttribute("articles",carpetRepository.findAll());
        return redirectView;
    }

    @PostMapping("/edit/post")
    public RedirectView editArticle(Carpet updated, @RequestParam(name = "categories", required = false) String categories, @RequestParam(name = "images", required = false) MultipartFile[] images, RedirectAttributes redir) {

        this.uploadImages(updated, images);
        carpetRepository.save(updated);
        if(categories != null) {
            for (String c : categories.split(",")) {
                categoryRepository.addCategoryToCarpet(Integer.valueOf(updated.getId()), Integer.valueOf(c));
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
        mp.addAttribute("article", carpetRepository.findById(Integer.valueOf(id)));
        List<Category> checked = categoryRepository.getCategoriesOfCarpet(Integer.valueOf(id));
        List<Category> notChecked = categoryRepository.getAllCategories();
        for(Category cat : checked) notChecked.remove(cat);
        mp.addAttribute("categories_checked", checked);
        mp.addAttribute("categories_not_checked", notChecked);
        return "admin/articleForm";
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable int id) {
        if(!carpetRepository.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        carpetRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public String deleteCarpet(ModelMap mp, @PathVariable String id) {
        Carpet carpet = carpetRepository.findId(Integer.parseInt(id));
        if(carpet == null){
            mp.addAttribute("msg_article_not_found", true);
        }else{
            photoStorageService.deleteFolder(photoStorageService.getRoot() + "/carpet" + carpet.getId());
            carpetRepository.delete(carpet);
            mp.addAttribute("articles", carpetRepository.findAll());
            mp.addAttribute("msg_article_deleted", true);
        }

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
