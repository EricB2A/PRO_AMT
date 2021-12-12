package com.example.amt_demo.controller;

import com.example.amt_demo.model.*;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTests {

    @Autowired
    private MockMvc mvc;

    @Mock
    private ArticleRepository articleRepository;


    @Test
    public void getWorks() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/cart"))
                .andExpect(status().isOk());
    }

    @Test
    public void getCartModelValid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("cartPrice"));
    }

    @Test
    @WithMockUser
    public void addAndRemoveProductToCartVisitor() throws Exception {
        Article article = new Article("Tapis", "Desc tapis", 100.);
        Mockito.when(articleRepository.findById(1L))
                .thenReturn(Optional.of(article));

        mvc.perform(MockMvcRequestBuilders.post("/cart/1")
                        .with(csrf())
                        .contentType("application/json")
                        .content("{\"quantity\": \"1\"}")
                )
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.delete("/cart/1")
                .with(csrf()))
                .andExpect(status().isOk());

    }

    @Test
    public void removeAllProductsFromCart() throws Exception {

        Article article = new Article("Tapis", "Desc tapis", 100.);
        Mockito.when(articleRepository.findById(1L))
                .thenReturn(Optional.of(article));

        Article article2 = new Article("Tapis 2", "Desc tapis 2", 200.);
        Mockito.when(articleRepository.findById(2L))
                .thenReturn(Optional.of(article2));

        mvc.perform(MockMvcRequestBuilders.post("/cart/1")
                        .with(csrf())
                        .contentType("application/json")
                        .content("{\"quantity\": \"1\"}")
                )
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.post("/cart/2")
                        .with(csrf())
                        .contentType("application/json")
                        .content("{\"quantity\": \"1\"}")
                )
                .andExpect(status().isOk());

        // Appell de la suppresion du panier
        mvc.perform(MockMvcRequestBuilders.delete("/cart")
                .with(csrf())
        )
                .andExpect(status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/cart"))
                .andExpect(model().attribute("articles", hasSize(0)))
                .andExpect(model().attributeExists("cartPrice"));
    }


}
