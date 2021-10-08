package com.example.amt_demo;

import com.example.amt_demo.model.Carpet;
import com.example.amt_demo.model.CarpetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@RequestMapping(path = "/carpets")
@Controller
public class CarpetController {

    

    @Autowired
    private CarpetRepository carpetRepository;

    @GetMapping(path="/")
    public @ResponseBody
    Iterable<Carpet> getAllCarpets() {
        return carpetRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public String getAllCarpets(ModelMap mp, @PathVariable String id) {
        mp.addAttribute("article", carpetRepository.findById(Integer.valueOf(id)));
        return "article";
    }

    /*
    @RequestMapping(value="/", method = RequestMethod.POST)
    @ResponseStatus(value= HttpStatus.OK)
    public Carpet newCarpet(@RequestParam(value="name", required = false) String name, @RequestParam(value = "description", required = false) String description, @RequestParam(value = "price", required = false) double price) {
        Carpet carpet = new Carpet();
        carpet.setName(name);
        carpet.setDescription(description);
        carpet.setPrice(price);
        return carpet;
    }
     */

    /*
    @PostMapping
    public ResponseEntity<?> newCarpet(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("price") double price, @RequestParam(value = "imagePath", required = false) String imagePath){
        Carpet carpet = new Carpet();
        carpet.setName(name);
        carpet.setDescription(description);
        carpet.setPrice(price);
        carpet.setImagePath(imagePath);

        carpetRepository.save(carpet);

        return new ResponseEntity<>(carpet, HttpStatus.OK);
    }
     */

    @PostMapping("")
    ResponseEntity<?> create(@RequestBody Carpet newCarpet) {
        carpetRepository.save(newCarpet);
        return new ResponseEntity<>(newCarpet, HttpStatus.OK);
    }

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

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable int id) {
        if(!carpetRepository.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        carpetRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
