package com.example.amt_demo.controller;

import com.example.amt_demo.model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTests {

    @Autowired
    private MockMvc mvc;

    @Mock
    private CartInfoRepository cartInfoRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    private User user;


    @Test
    @WithMockUser(roles={"user"})
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
    @WithMockUser(roles={"user"})


    @BeforeAll
    public static void CartControllerTest_init() {

    }

    @AfterAll
    public static void cleanUp(@Autowired CartInfoRepository cartInfoRepository, @Autowired ArticleRepository articleRepository) {
        cartInfoRepository.deleteAll();
        articleRepository.deleteAll();
    }


}
