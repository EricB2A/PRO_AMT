/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CategoryControllerTest.java
 *
 * @brief Unit tests about the category controller
 */

package com.example.amt_demo.controller;

import com.example.amt_demo.model.Article;
import com.example.amt_demo.model.Category;
import com.example.amt_demo.service.CategoryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

    private static List<Category> mockCategory;
    private static final List<Article> mockArticle = new ArrayList<>();

    /**
     * Initializing the mock data with dummy categories & carpets
     */
    @BeforeAll
    public static void CategoryControllerTest_init() {
        Category turkish = new Category(1, "Turkish");
        Category arabic = new Category(2, "Arabic");
        Category orient = new Category(3, "Orient");

        mockCategory = new ArrayList<>(Arrays.asList(turkish, arabic, orient));

        for(int i = 1; i <= 4; i++) {
            Article article = new Article("test name " + i, "test desc " + i, i * 10.00);
            if(i % 2 == 0) {
                article.getCategories().add(turkish);
                turkish.addCarpet(article);
            } else {
                article.getCategories().add(arabic);
                arabic.addCarpet(article);
            }
            mockArticle.add(article);
        }
    }

    /**
     * Test about fetching every existing category in the database
     * @throws Exception
     */
    @Test
    @WithMockUser(roles={"admin"})
    public void CategoryControllerTest_getAllCategories() throws Exception {
        Mockito.when(categoryService.findAll()).thenReturn(mockCategory);

        mvc.perform(MockMvcRequestBuilders.get("/admin/categories"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/categories"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/categories.jsp"))
                .andExpect(model().attribute("categories", hasSize(3)))
                .andExpect(model().attribute("categories", hasItem(
                        allOf(
                                hasProperty("name", is("Orient"))
                        )
                )))
                .andExpect(model().attribute("categories", hasItem(
                        allOf(
                                hasProperty("name", is("Arabic"))
                        )
                )))
                .andExpect(model().attribute("categories", hasItem(
                        allOf(
                                hasProperty("name", is("Turkish"))
                        )
                )));
    }

    /**
     * Test about displaying the form for adding a category
     * @throws Exception
     */
    @Test
    @WithMockUser(roles={"admin"})
    public void CategoryControllerTest_formShouldAppearOnAddition() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/categories/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/categoryForm"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/categoryForm.jsp"));
    }

    /**
     * Test about adding a category with a name
     * @throws Exception
     */
    @Test
    @WithMockUser(roles={"admin"})
    public void CategoryControllerTest_addNewCategory() throws Exception {
        Category moroccan = new Category(4, "Moroccan");
        mockCategory.add(moroccan);

        Mockito.when(categoryService.findAll()).thenReturn(mockCategory);

        mvc.perform(MockMvcRequestBuilders.post("/admin/categories/add/post")
                        .with(csrf())
                        .param("name", "Moroccan"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/categories"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/categories.jsp"))
                .andExpect(model().attribute("categories", hasItem(
                        allOf(
                                hasProperty("name", is("Moroccan"))
                        )
                )));
    }

    /**
     * Test about adding a category with an existing name
     * @throws Exception
     */
    @Test
    @WithMockUser(roles={"admin"})
    public void CategoryControllerTest_cannotInsertSameNameCategory() throws Exception {
        Category first = new Category(1, "Turkish");

        Mockito.when(categoryService.findByName(first.getName())).thenReturn(mockCategory.get(0));
        Mockito.when(categoryService.findAll()).thenReturn(mockCategory);
        mvc.perform(MockMvcRequestBuilders.post("/admin/categories/add/post")
                        .with(csrf())
                        .param("name", "Turkish"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/categories"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/categories.jsp"))
                .andExpect(model().attribute("error", "Cette catégorie existe déjà"));
    }

    /**
     * Test about displaying the form for adding a category
     * @throws Exception
     */
    @Test
    @WithMockUser(roles={"admin"})
    public void CategoryControllerTest_formShouldAppearOnEdition() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/categories/edit/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/categoryForm"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/categoryForm.jsp"));
    }


    /**
     * Test about deleting a category associated with 0 article
     * @throws Exception
     */
    @Test
    @WithMockUser(roles={"admin"})
    public void CategoryControllerTest_deleteCategoryWithoutArticlesInIt() throws Exception {
        // On souhaite supprimer la catégorie "Orient" qui n'a pas d'articles associés
        Set<Category> temp = new HashSet<>();

        for(Article c : mockArticle) {
            if (c.getCategories().contains(mockCategory.get(2))) {
                temp.add(mockCategory.get(2));
            }
        }

        Mockito.when(categoryService.findById(mockCategory.get(2).getId())).thenReturn(mockCategory.get(2));
        Mockito.when(categoryService.hasArticlesInCategory(mockCategory.get(2).getId())).thenReturn(temp);

        mockCategory.remove(mockCategory.get(2));
        Mockito.when(categoryService.findAll()).thenReturn(mockCategory);

        mvc.perform(MockMvcRequestBuilders.get("/admin/categories/delete/{id}", 3)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/categories"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/categories.jsp"))
                .andExpect(model().attribute("categories", hasSize(2)))
                .andExpect(model().attribute("categories", hasItem(
                        allOf(
                                hasProperty("name", is("Arabic"))
                        )
                )))
                .andExpect(model().attribute("categories", hasItem(
                        allOf(
                                hasProperty("name", is("Turkish"))
                        )
                )));
    }

    /**
     * Test about deleting a category associated with 1+ article(s)
     * Should return an error client-side with list of articles within category
     * @throws Exception
     */
    @Test
    @WithMockUser(roles={"admin"})
    public void CategoryControllerTest_deleteCategoryWithArticlesInIt() throws Exception {
        //On souhaite supprimer la catégorie "Turkish" qui a 2 articles associés
        Set<Category> temp = new HashSet<>();

        //TODO Voir remarque plus haut
        for(Article c : mockArticle) {
            if (c.getCategories().contains(mockCategory.get(0))) {
                temp.add(mockCategory.get(0));
            }
        }

        Mockito.when(categoryService.findById(mockCategory.get(0).getId())).thenReturn(mockCategory.get(0));
        Mockito.when(categoryService.hasArticlesInCategory(mockCategory.get(0).getId())).thenReturn(temp);

        mvc.perform(MockMvcRequestBuilders.get("/admin/categories/delete/{id}", 1)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/categories"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/categories.jsp"))
                .andExpect(model().attribute("error", "Vous ne pouvez pas supprimer des catégories qui contiennent des articles"));
    }
}