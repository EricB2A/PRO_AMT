package com.example.amt_demo.api;


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

@RequestMapping(path = "/api/carpets")
@Controller
public class CarpetControllerAPI {

    @Autowired
    private CarpetRepository carpetRepository;

    @GetMapping(path="/", produces = {"application/json"})
    public ResponseEntity<?> getAllCarpets() {
        return new ResponseEntity<>(carpetRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path="/{id}", produces = {"application/json"})
    public ResponseEntity<?> getAllCarpets(ModelMap mp, @PathVariable int id) {
        return new ResponseEntity<>(carpetRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> create(@RequestBody Carpet newCarpet) {
        carpetRepository.save(newCarpet);
        return new ResponseEntity<>(newCarpet, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable int id) {
        if(!carpetRepository.existsById(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        carpetRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
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
}
