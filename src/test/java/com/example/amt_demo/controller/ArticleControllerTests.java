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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
        Category commonForBoth = new Category("Arabic");
        Mockito.when(categoryService.getCategoriesOfCarpet(2)).thenReturn(
            Stream.of(commonForBoth)
                .collect(Collectors.toList())
        );

        Mockito.when(categoryService.getAllCategories()).thenReturn(
            Stream.of(commonForBoth, new Category("Turkish"), new Category("Oriental"))
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
            .andExpect(model().attribute("categories_checked", hasSize(1)))
            .andExpect(model().attribute("categories_checked", hasItem(
                allOf(
                    hasProperty("name", is("Arabic"))
                )
            )))
            .andExpect(model().attribute("categories_not_checked", hasSize(2)))
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



    @Test
    @WithMockUser(roles={"admin"})
    public void ArticleControllerTest_editArticleFormPostErrorSameName() throws Exception {

        Article updated = new Article(4,"Carpet2", "Any description", 80.0, 40);

        Mockito.when(articleService.findByName("Carpet2")).thenReturn(
           new Article(2,"Carpet2", "Carpet2 description", 50.0, 20)
        );

        mvc.perform(MockMvcRequestBuilders.post("/admin/articles/edit/post")
            .with(csrf())
                .param("id", String.valueOf(updated.getId()))
                .param("name", updated.getName())
                .param("description", updated.getDescription())
                .param("price", String.valueOf(updated.getPrice()))
                .param("quantity", String.valueOf(updated.getQuantity()))
            )
            .andExpect(redirectedUrl("/admin/articles/edit/4"))
            .andExpect(flash().attribute("msg_already_existing_article", true));
    }

    @Test
    @WithMockUser(roles={"admin"})
    public void ArticleControllerTest_editArticleFormPostOk() throws Exception {

        Article updated = new Article(4,"Carpet2", "Any description", 80.0, 40);

        Mockito.when(articleService.findByName("Carpet2")).thenReturn(
                new Article(4,"Carpet2", "Any description", 80.0, 40)
        );

        MockMultipartFile emptyPhoto = new MockMultipartFile("images", "", "application/json", "{\"image\": \"\"}".getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart("/admin/articles/edit/post")
                .file(emptyPhoto)
                .with(csrf())
                .param("id", String.valueOf(updated.getId()))
                .param("name", updated.getName())
                .param("description", updated.getDescription())
                .param("price", String.valueOf(updated.getPrice()))
                .param("quantity", String.valueOf(updated.getQuantity()))
                )
            .andExpect(redirectedUrl("/admin/articles/edit/4"))
            .andExpect(flash().attribute("msg_article_edited", true));
    }

    @Test
    @WithMockUser(roles={"admin"})
    public void ArticleControllerTest_editArticleFormPostOk3Photos() throws Exception {

        Article initial = new Article(4,"Carpet2", "Any description", 80.0, 40);
        Article updated = new Article(4,"Carpet2 updated", "Any description", 80.0, 40);

        Mockito.when(articleService.findByName("Carpet2")).thenReturn(initial);

        MockMultipartFile file1 = new MockMultipartFile("images", "carpet_photo1.jpg", "application/json", "{\"image\": \"sdfghjk\"}".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("images", "carpet_photo2.jpg", "application/json", "{\"image\": \"sdfghjk\"}".getBytes());
        MockMultipartFile file3 = new MockMultipartFile("images", "carpet_photo3.jpg", "application/json", "{\"image\": \"sdfghjk\"}".getBytes());

        List<ArticlePhoto> photos = Stream.of(
                new ArticlePhoto(file1.getOriginalFilename()),
                new ArticlePhoto(file2.getOriginalFilename()),
                new ArticlePhoto(file3.getOriginalFilename())
        ).collect(Collectors.toList());

        updated.setPhotos(photos);
        Mockito.when(articleService.findById(4)).thenReturn(Optional.of(updated));
        // Post and Redirect
        mvc.perform(MockMvcRequestBuilders.multipart("/admin/articles/edit/post")
                .file(file1)
                .file(file2)
                .file(file3)
                .with(csrf())
                .param("id", String.valueOf(initial.getId()))
                .param("name", initial.getName() + " updated")
                .param("description", initial.getDescription())
                .param("price", String.valueOf(initial.getPrice()))
                .param("quantity", String.valueOf(initial.getQuantity())))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/admin/articles/edit/4"))
            .andExpect(flash().attribute("msg_article_edited", true));

        // Get with Updated
        mvc.perform(MockMvcRequestBuilders.get("/admin/articles/edit/4"))
            .andExpect(status().isOk())
            .andExpect(view().name("admin/articleForm"))
            .andExpect(forwardedUrl("/WEB-INF/jsp/admin/articleForm.jsp"))
            .andExpect(model().attribute("editing", true))
            .andExpect(model().attribute("post_url", "/admin/articles/edit/post"))
            .andExpect(model().attribute("article",
                allOf(
                    hasProperty("name", is(updated.getName())),
                    hasProperty("description", is(updated.getDescription())),
                    hasProperty("price", is(updated.getPrice())),
                    hasProperty("quantity", is(updated.getQuantity())),
                    hasProperty("photos", hasItem(
                            allOf(
                                    hasProperty("path", is("carpet_photo1.jpg"))
                            )
                    )),
                    hasProperty("photos", hasItem(
                            allOf(
                                    hasProperty("path", is("carpet_photo2.jpg"))
                            )
                    )),
                    hasProperty("photos", hasItem(
                            allOf(
                                    hasProperty("path", is("carpet_photo3.jpg"))
                            )
                    ))
                )
            ));
    }


    /**
     *
     * @param articleRepository
     */
    @AfterAll
    static void cleanUp(@Autowired ArticleRepository articleRepository) {
        articleRepository.deleteAll();
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}