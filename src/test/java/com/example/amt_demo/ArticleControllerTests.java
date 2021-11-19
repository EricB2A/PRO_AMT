package com.example.amt_demo;

import com.example.amt_demo.model.*;
import com.example.amt_demo.service.CarpetService;
import com.example.amt_demo.service.CategoryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTests {

    @Autowired
    private MockMvc mvc;

    @Mock
    private CarpetRepository carpetRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @MockBean
    private CarpetService carpetService;

    @MockBean
    private CategoryService categoryService;

    private static List<Carpet> mockCarpet = new ArrayList<>();
    private static List<Category> mockCategory;

    @BeforeAll
    public static void CarpetControllerTest_init() {
        Category turkish = new Category("Turkish");
        Category arabic = new Category("Arabic");

        mockCategory = Arrays.asList(turkish, arabic);

        for(int i = 1; i <= 6; i++) {
            Carpet carpet = new Carpet("test name " + i, "test desc " + i, i * 10.00);
            if(i % 2 == 0) {
                carpet.getCategories().add(turkish);
            } else {
                carpet.getCategories().add(arabic);
            }
            for (int j = 1; j <= 6; ++j) {
                carpet.getPhotos().add(new CarpetPhoto("/carpet-photos/carpet" + i + "/"+"carpet" + j + ".jpg"));
            }
            mockCarpet.add(carpet);
        }
    }

    //WORKS
    @Test
    @WithMockUser(roles={"admin"})
    public void ArticleControllerTest_getAllArticles() throws Exception {

        Mockito.when(carpetService.findAll()).thenReturn(mockCarpet);

        mvc.perform(MockMvcRequestBuilders.get("/admin/articles"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/articles"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/articles.jsp"))
                .andExpect(model().attribute("articles", hasSize(6)))
                .andExpect(model().attribute("articles", hasItem(
                        allOf(
                                hasProperty("name", is("test name 1")),
                                hasProperty("description", is("test desc 1")),
                                hasProperty("price", is(10.0))
                        )
                )))
                .andExpect(model().attribute("articles", hasItem(
                        allOf(
                                hasProperty("name", is("test name 5")),
                                hasProperty("description", is("test desc 5")),
                                hasProperty("price", is(50.0))
                        )
                )))
                .andExpect(model().attribute("articles", hasItem(
                        allOf(
                                hasProperty("name", is("test name 4")),
                                hasProperty("description", is("test desc 4")),
                                hasProperty("price", is(40.0))
                        )
                )));
    }

    @Test
    public void ArticleControllerTest_deleteArticle() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/admin/articles/delete/{id}"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/articles"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/articles.jsp"))
                .andExpect(model().attribute("articles", hasSize(6)))
                .andExpect(model().attribute("articles", hasItem(
                        allOf(
                                hasProperty("name", is("test name 1")),
                                hasProperty("description", is("test desc 1")),
                                hasProperty("price", is(10.0))
                        )
                )))
                .andExpect(model().attribute("articles", hasItem(
                        allOf(
                                hasProperty("name", is("test name 5")),
                                hasProperty("description", is("test desc 5")),
                                hasProperty("price", is(50.0))
                        )
                )))
                .andExpect(model().attribute("articles", hasItem(
                        allOf(
                                hasProperty("name", is("test name 4")),
                                hasProperty("description", is("test desc 4")),
                                hasProperty("price", is(40.0))
                        )
                )));
    }

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
