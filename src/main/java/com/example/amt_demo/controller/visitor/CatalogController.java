package com.example.amt_demo.controller.visitor;

import com.example.amt_demo.model.*;
import com.example.amt_demo.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RequestMapping(path = "/catalog")
@Controller
public class CatalogController {

    @Autowired
    private CarpetRepository carpetRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(path="", produces = {"application/xml"})
    public String getAllCarpets(ModelMap mp) {
        this.generateDummyData();
        mp.addAttribute("carpets", carpetRepository.findAll());
        mp.addAttribute("categories", categoryRepository.findAll());
        return "public/catalog";
    }

    private void generateDummyData(){
        carpetRepository.deleteAll();
        categoryRepository.deleteAll();
        Category turkish = new Category("Turkish");
        Category arabic = new Category("Arabic");
        categoryRepository.save(turkish);
        categoryRepository.save(arabic);
        for(int i = 1; i <= 6; i++) {
            Carpet carpet = new Carpet("test name " + i, "test desc " + i, i * 10.00);
            if(i % 2 == 0) {
                carpet.getCategories().add(turkish);
            }else{
                carpet.getCategories().add(arabic);
            }
            for (int j = 1; j <= 6; ++j) {
                carpet.getPhotos().add(new CarpetPhoto("/carpet-photos/carpet"+i+"/"+"carpet"+j+".jpg"));

            }
            carpetRepository.save(carpet);
        }
    }

    @GetMapping(path="/{id}", produces = {"application/xml"})
    public String getCarpet(ModelMap mp, @PathVariable String id) {
        mp.addAttribute("carpet", carpetRepository.findById(Integer.valueOf(id)));
        return "public/article";
    }

    @GetMapping(path="/filter/{name}", produces = {"application/xml"})
    public String getCarpetsFilter(ModelMap mp, @PathVariable String name) {
        mp.addAttribute("carpets", carpetRepository.findByFilter(name));
        mp.addAttribute("categories", categoryRepository.findAll());
        mp.addAttribute("filter", name);
        return "public/catalog";
    }

}
