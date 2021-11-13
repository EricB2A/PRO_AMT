package com.example.amt_demo;

import com.example.amt_demo.model.*;
import com.example.amt_demo.service.PhotoUploadService;
import com.example.amt_demo.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


@RequestMapping(path = "/admin/carpets")
@Controller
public class ArticleController {

    @Autowired
    private CarpetRepository carpetRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    PhotoUploadService photoStorageService;

    @GetMapping(path="/{id}", produces = {"application/xml"})
    public String getAllCarpets(ModelMap mp, @PathVariable String id) {
        mp.addAttribute("article", carpetRepository.findById(Integer.valueOf(id)));
        return "article";
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable int id) {
        if(!carpetRepository.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        carpetRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/add")
    public String addCarpetForm(ModelMap mp) {

        mp.addAttribute("adding", true);
        mp.addAttribute("post_url", "/admin/carpets/add/post");
        return "admin/articleForm";
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
        RedirectView redirectView = new RedirectView("/admin/carpets/edit/"+carpet_id,true);
        redir.addFlashAttribute("msg_photo_deleted",true);
        return redirectView;
    }

    @PostMapping("/add/post")
    public RedirectView addCarpet(Carpet newCarpet, @RequestParam("images") MultipartFile[] images, RedirectAttributes redir) {
        // TODO : Image upload
        carpetRepository.save(newCarpet);
        this.uploadImages(newCarpet, images);
        carpetRepository.save(newCarpet);

        RedirectView redirectView = new RedirectView("/admin/all",true);
        redir.addFlashAttribute("msg_article_added",true);
        return redirectView;
    }

    @GetMapping("/edit/{id}")
    public String editArticle(ModelMap mp, @PathVariable String id) {
        mp.addAttribute("editing", true);
        mp.addAttribute("post_url", "/admin/carpets/edit/post");
        mp.addAttribute("article", carpetRepository.findById(Integer.valueOf(id)));
        mp.addAttribute("categories", categoryRepository.getCategoriesByCarpet(Integer.valueOf(id)));
        return "admin/articleForm";
    }

    @PostMapping("/edit/post")
    public RedirectView editArticle(Carpet updated, @RequestParam(name = "categories", required = false) String categories, @RequestParam(name = "images", required = false) MultipartFile[] images, RedirectAttributes redir) {
        carpetRepository.save(updated);
        this.uploadImages(updated, images);
        carpetRepository.save(updated);
        if(categories != null) {
            for (String c : categories.split(",")) {
                categoryRepository.addCategoryToCarpet(Integer.valueOf(updated.getId()), Integer.valueOf(c));
            }
        }
        RedirectView redirectView = new RedirectView("/admin/carpets/edit/"+updated.getId(),true);
        redir.addFlashAttribute("msg_article_edited",true);
        return redirectView;
    }

    @GetMapping("/delete/{id}")
    public String deleteCarpet(ModelMap mp, @PathVariable String id) {
        Carpet carpet = carpetRepository.findId(Integer.parseInt(id));

        carpetRepository.delete(carpet);

        mp.addAttribute("categories", categoryRepository.findAll());
        mp.addAttribute("articles", carpetRepository.findAll());
        mp.addAttribute("msg_article_deleted", true);
        return "admin/admin";
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
