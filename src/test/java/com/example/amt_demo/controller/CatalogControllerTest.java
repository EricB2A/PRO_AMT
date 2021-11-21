/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CategoryControllerTest.java
 *
 * @brief Unit tests about the category controller
 */

package com.example.amt_demo.controller;

import com.example.amt_demo.model.Article;
import com.example.amt_demo.model.ArticleRepository;
import com.example.amt_demo.model.Category;
import com.example.amt_demo.model.CategoryRepository;
import com.example.amt_demo.service.ArticleService;
import com.example.amt_demo.service.CategoryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CatalogControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleRepository articleRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    /**
     * Initializing the mock data with dummy categories & carpets
     */
    @BeforeAll
    public static void CategoryControllerTest_init() {

    }

    @Test
    @WithAnonymousUser
    public void CatalogControllerTest_getAll() throws Exception {

        Category turkish = new Category(1, "Turkish");
        Category arabic = new Category(2, "Arabic");
        Category orient = new Category(3, "Orient");

        LinkedList<Category> categories = new LinkedList<>(Arrays.asList(turkish, arabic, orient));

        Article carpet1 = new Article(1, "Carpet 1", "Carpet desc", 30.0, 10);
        Article carpet2 = new Article(2, "Carpet 2", "Carpet desc", 30.0, 10);
        Article turkishCarpet = new Article(3, "Turkish carpet 1", "Turkish carpet desc", 40.0, 10);
        Article turkishCarpet2 = new Article(4, "Turkish carpet 2", "Turkish carpet desc", 30.0, 10);
        Article arabicCarpet1 = new Article(5, "Arabic carpet 1", "Arabic carpet desc", 30.0, 10);
        Article arabicCarpet2 = new Article(6, "Arabic carpet 2", "Arabic carpet desc", 30.0, 10);
        Article orientCarpet1 = new Article(7, "Orient carpet 1", "Carpet desc", 30.0, 10);
        Article orientCarpet2 = new Article(8, "Orient carpet 2", "Carpet desc", 30.0, 10);

        turkishCarpet.setCategories(Stream.of(turkish).collect(Collectors.toSet()));
        turkishCarpet2.setCategories(Stream.of(turkish).collect(Collectors.toSet()));
        turkish.addCarpet(turkishCarpet);
        turkish.addCarpet(turkishCarpet2);

        arabicCarpet1.setCategories(Stream.of(arabic).collect(Collectors.toSet()));
        arabicCarpet2.setCategories(Stream.of(arabic).collect(Collectors.toSet()));
        arabic.addCarpet(arabicCarpet1);
        arabic.addCarpet(arabicCarpet2);

        orientCarpet1.setCategories(Stream.of(orient).collect(Collectors.toSet()));
        orientCarpet2.setCategories(Stream.of(orient).collect(Collectors.toSet()));
        orient.addCarpet(orientCarpet1);
        orient.addCarpet(orientCarpet2);

        List<Article> articles = Stream.of(turkishCarpet, turkishCarpet2, arabicCarpet1, arabicCarpet2, carpet1, carpet2, orientCarpet1, orientCarpet2).collect(Collectors.toList());

        Mockito.when(articleRepository.findAll()).thenReturn(articles);
        Mockito.when(categoryRepository.notEmptyCategory()).thenReturn(categories);

        mvc.perform(MockMvcRequestBuilders.get("/catalog"))
                .andExpect(status().isOk())
                .andExpect(view().name("public/catalog"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/public/catalog.jsp"))
                .andExpect(model().attribute("articles", hasSize(8)))
                .andExpect(model().attribute("categories", hasSize(3)))
                .andExpect(model().attribute("articles", hasItem(
                        allOf(
                                hasProperty("name", is("Turkish carpet 1")),
                                hasProperty("description", is("Turkish carpet desc")),
                                hasProperty("price", is(40.0)),
                                hasProperty("quantity", is(10)),
                                hasProperty("categories",  hasItem(
                                        allOf(
                                                hasProperty("name", is("Turkish"))
                                        )
                                ))
                        )
                )))
                .andExpect(model().attribute("articles", hasItem(
                        allOf(
                                hasProperty("name", is("Arabic carpet 2")),
                                hasProperty("description", is("Arabic carpet desc")),
                                hasProperty("price", is(30.0)),
                                hasProperty("quantity", is(10)),
                                hasProperty("categories",  hasItem(
                                        allOf(
                                                hasProperty("name", is("Arabic"))
                                        )
                                ))
                        )
                )))
                .andExpect(model().attribute("articles", hasItem(
                        allOf(
                                hasProperty("name", is("Orient carpet 1")),
                                hasProperty("description", is("Carpet desc")),
                                hasProperty("price", is(30.0)),
                                hasProperty("quantity", is(10)),
                                hasProperty("categories",  hasItem(
                                        allOf(
                                                hasProperty("name", is("Orient"))
                                        )
                                ))
                        )
                )));
    }

    @Test
    @WithAnonymousUser
    public void CatalogControllerTest_getFiltered() throws Exception {

        Category turkish = new Category(1, "Turkish");
        Category arabic = new Category(2, "Arabic");
        Category orient = new Category(3, "Orient");

        LinkedList<Category> categories = new LinkedList<>(Arrays.asList(turkish, arabic, orient));

        Article arabic1 = new Article(5, "Arabic carpet 1", "Arabic carpet desc", 30.0, 10);
        Article arabic2 = new Article(6, "Arabic carpet 2", "Arabic carpet desc", 30.0, 10);

        arabic1.setCategories(Stream.of(arabic).collect(Collectors.toSet()));
        arabic2.setCategories(Stream.of(arabic).collect(Collectors.toSet()));
        arabic.addCarpet(arabic1);
        arabic.addCarpet(arabic2);


        Mockito.when(articleRepository.findByFilter("Arabic")).thenReturn(
                Stream.of(
                        arabic1, arabic2
                ).collect(Collectors.toSet())
        );
        Mockito.when(categoryRepository.notEmptyCategory()).thenReturn(categories);

        mvc.perform(MockMvcRequestBuilders.get("/catalog/filter/Arabic"))
                .andExpect(status().isOk())
                .andExpect(view().name("public/catalog"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/public/catalog.jsp"))
                .andExpect(model().attribute("filter", is("Arabic")))
                .andExpect(model().attribute("articles", hasSize(2)))
                .andExpect(model().attribute("categories", hasSize(3)))
                .andExpect(model().attribute("articles", hasItem(
                        allOf(
                                hasProperty("name", is("Arabic carpet 1")),
                                hasProperty("description", is("Arabic carpet desc")),
                                hasProperty("price", is(30.0)),
                                hasProperty("quantity", is(10)),
                                hasProperty("categories",  hasItem(
                                        allOf(
                                                hasProperty("name", is("Arabic"))
                                        )
                                ))
                        )
                )))
                .andExpect(model().attribute("articles", hasItem(
                        allOf(
                                hasProperty("name", is("Arabic carpet 2")),
                                hasProperty("description", is("Arabic carpet desc")),
                                hasProperty("price", is(30.0)),
                                hasProperty("quantity", is(10)),
                                hasProperty("categories",  hasItem(
                                        allOf(
                                                hasProperty("name", is("Arabic"))
                                        )
                                ))
                        )
                )));
    }

    @Test
    @WithAnonymousUser
    public void CatalogControllerTest_getDetailedView() throws Exception {

        Category arabic = new Category(2, "Arabic");
        Article arabic1 = new Article(5, "Arabic carpet 1", "Arabic carpet desc", 30.0, 10);

        arabic1.setCategories(Stream.of(arabic).collect(Collectors.toSet()));
        arabic.addCarpet(arabic1);

        Mockito.when(articleRepository.findById(5)).thenReturn(
                Optional.of(arabic1)
        );

        mvc.perform(MockMvcRequestBuilders.get("/catalog/5"))
                .andExpect(status().isOk())
                .andExpect(view().name("public/article"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/public/article.jsp"))
                .andExpect(model().attribute("article",
                        allOf(
                                hasProperty("name", is("Arabic carpet 1")),
                                hasProperty("description", is("Arabic carpet desc")),
                                hasProperty("price", is(30.0)),
                                hasProperty("quantity", is(10)),
                                hasProperty("categories",  hasItem(
                                        allOf(
                                                hasProperty("name", is("Arabic"))
                                        )
                                ))
                        )
                ));
    }

}
