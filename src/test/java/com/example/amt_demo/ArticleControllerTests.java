package com.example.amt_demo;

import com.example.amt_demo.model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CarpetRepository carpetRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void CarpetControllerTests_contextLoads() {
    }

    @Test
    void AddDummyData() {
        this.generateDummyData();
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

    @AfterAll
    static void cleanUp(@Autowired CarpetRepository carpetRepository) {
        carpetRepository.deleteAll();
    }

}
