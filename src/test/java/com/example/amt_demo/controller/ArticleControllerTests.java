/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file ArticleController.java
 *
 * @brief
 */

package com.example.amt_demo.controller;

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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
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




    /**
     *
     */
    @BeforeAll
    public static void CarpetControllerTest_init() {

    }

    /**
     *
     * @throws Exception
     */
    @Test
    @WithMockUser(roles={"admin"})
    public void ArticleControllerTest_getAllArticles() throws Exception {

        List<Article> mockArticle = new ArrayList<>();
        Category turkish = new Category("Turkish");
        Category arabic = new Category("Arabic");

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
    @WithMockUser(roles={"admin"})
    public void ArticleControllerTest_deleteArticle() throws Exception {

        Mockito.when(articleService.findById(2)).thenReturn(
                Optional.of(new Article(2,"Carpet2", "Carpet2 description", 50.0, 20))
        );

        Mockito.when(articleService.findAll()).thenReturn(Stream.of(
                new Article(1,"Carpet1", "Carpet1 description", 40.0, 20),
                new Article(3,"Carpet3", "Carpet3 description", 60.0, 20)
        ).collect(Collectors.toList()));

        mvc.perform(MockMvcRequestBuilders.get("/admin/articles/delete/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/articles"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/articles.jsp"))
                .andExpect(model().attribute("msg_article_deleted", true))
                .andExpect(model().attribute("articles", hasSize(2)))
                .andExpect(model().attribute("articles", hasItem(
                        allOf(
                                hasProperty("name", is("Carpet1")),
                                hasProperty("description", is("Carpet1 description")),
                                hasProperty("price", is(40.0)),
                                hasProperty("quantity", is(20))
                        )
                )))
                .andExpect(model().attribute("articles", hasItem(
                        allOf(
                                hasProperty("name", is("Carpet3")),
                                hasProperty("description", is("Carpet3 description")),
                                hasProperty("price", is(60.0)),
                                hasProperty("quantity", is(20))
                        )
                )));
    }

    @Test
    @WithMockUser(roles={"admin"})
    public void ArticleControllerTest_editFormArticle() throws Exception {

        Mockito.when(articleService.findById(2)).thenReturn(
                Optional.of(new Article(2,"Carpet2", "Carpet2 description", 50.0, 20))
        );

        Mockito.when(categoryService.getCategoriesOfCarpet(2)).thenReturn(
                Stream.of(new Category("Arabic"))
                .collect(Collectors.toList())
        );

        Mockito.when(categoryService.getAllCategories()).thenReturn(
                Stream.of(new Category("Arabic"), new Category("Turkish"), new Category("Oriental"))
                        .collect(Collectors.toList())
        );

        mvc.perform(MockMvcRequestBuilders.get("/admin/articles/edit/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/articleForm"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/articleForm.jsp"))
                .andExpect(model().attribute("editing", true))
                .andExpect(model().attribute("post_url", "/admin/articles/edit/post"))
                .andExpect(model().attribute("article",
                        allOf(
                                hasProperty("name", is("Carpet2")),
                                hasProperty("description", is("Carpet2 description")),
                                hasProperty("price", is(50.0)),
                                hasProperty("quantity", is(20))
                        )
                ))
                .andExpect(model().attribute("categories_checked", hasItem(
                    allOf(
                            hasProperty("name", is("Arabic"))
                    )
                )))
                .andExpect(model().attribute("categories_not_checked", hasItem(
                        allOf(
                                hasProperty("name", is("Turkish"))
                        )
                )))
                .andExpect(model().attribute("categories_not_checked", hasItem(
                        allOf(
                                hasProperty("name", is("Oriental"))
                        )
                )));
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