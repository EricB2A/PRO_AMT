/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file ArticleController.java
 *
 * @brief
 */

package com.example.amt_demo;

import com.example.amt_demo.model.*;
import com.example.amt_demo.service.ArticleService;
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
    private ArticleRepository articleRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private CategoryService categoryService;

    private static List<Article> mockArticle = new ArrayList<>();
    private static List<Category> mockCategory;

    /**
     *
     */
    @BeforeAll
    public static void CarpetControllerTest_init() {
        Category turkish = new Category("Turkish");
        Category arabic = new Category("Arabic");

        mockCategory = Arrays.asList(turkish, arabic);

        for(int i = 1; i <= 6; i++) {
            Article article = new Article("test name " + i, "test desc " + i, i * 10.00);
            if(i % 2 == 0) {
                article.getCategories().add(turkish);
            } else {
                article.getCategories().add(arabic);
            }
            for (int j = 1; j <= 6; ++j) {
                article.getPhotos().add(new ArticlePhoto("/carpet-photos/carpet" + i + "/"+"carpet" + j + ".jpg"));
            }
            mockArticle.add(article);
        }
    }

    /**
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(roles={"admin"})
    public void ArticleControllerTest_getAllArticles() throws Exception {

        Mockito.when(articleService.findAll()).thenReturn(mockArticle);

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

    /**
     *
     * @throws Exception
     */
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

    //TODO DELETE THIS IF A SCRIPT WE HAVE
    void AddDummyData() {
        this.generateDummyData();
    }

    private void generateDummyData(){
        articleRepository.deleteAll();
        categoryRepository.deleteAll();
        Category turkish = new Category("Turkish");
        Category arabic = new Category("Arabic");
        categoryRepository.save(turkish);
        categoryRepository.save(arabic);
        for(int i = 1; i <= 6; i++) {
            Article article = new Article("test name " + i, "test desc " + i, i * 10.00);
            if(i % 2 == 0) {
                article.getCategories().add(turkish);
            }else{
                article.getCategories().add(arabic);
            }
            for (int j = 1; j <= 6; ++j) {
                article.getPhotos().add(new ArticlePhoto("/carpet-photos/carpet"+i+"/"+"carpet"+j+".jpg"));

            }
            articleRepository.save(article);
        }
    }

    /**
     *
     * @param articleRepository
     */
    @AfterAll
    static void cleanUp(@Autowired ArticleRepository articleRepository) {
        articleRepository.deleteAll();
    }
}