package com.example.amt_demo;

import com.example.amt_demo.model.Carpet;
import com.example.amt_demo.model.CarpetRepository;
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


@RequestMapping(path = "/carpets")
@Controller
public class CarpetController {

    @Autowired
    private CarpetRepository carpetRepository;

    @GetMapping(path="/", produces = {"application/xml"})
    public String getAllCarpets(ModelMap mp) {
        mp.addAttribute("articles", carpetRepository.findAll());
        return "home";
    }

    @GetMapping(path="/{id}", produces = {"application/xml"})
    public String getAllCarpets(ModelMap mp, @PathVariable String id) {
        mp.addAttribute("article", carpetRepository.findById(Integer.valueOf(id)));
        return "article";
    }

    @PostMapping(value = "")
     public String saveCarpet(Carpet newCarpet, @RequestParam("image") MultipartFile multipartFile, ModelMap mp) throws IOException {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        newCarpet.setImagePath(fileName);

        carpetRepository.save(newCarpet);
        mp.addAttribute("articles", carpetRepository.findAll());

        String uploadDir = "carpet-photos/" + newCarpet.getId();

        FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);

        return "home";

        //return new ResponseEntity<>(newCarpet, HttpStatus.OK);
    }

    @GetMapping("/new")
    public String getCarpetRepository(ModelMap mp) {
        return "articleForm";
    }

    /*
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Carpet newCarpet) {
        return carpetRepository.findById(id)
                .map(carpet-> {
                    carpet.setName(newCarpet.getName());
                    carpet.setDescription(newCarpet.getDescription());
                    carpet.setPrice(newCarpet.getPrice());
                    carpet.setImagePath(newCarpet.getImagePath());
                    carpetRepository.save(carpet);
                    return new ResponseEntity<>(carpet, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    newCarpet.setId(id);
                    carpetRepository.save(newCarpet);
                    return new ResponseEntity<>(newCarpet, HttpStatus.OK);
                });
    }

     */
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable int id) {
        if(!carpetRepository.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        carpetRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
