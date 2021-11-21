package com.example.amt_demo.controller;

import com.example.amt_demo.model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

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

    @BeforeAll
    public void CartControllerTest_init() {
        this.user = userRepository.findByUsername("silkyroad");
    }

    @AfterAll
    void cleanUp(@Autowired CartInfoRepository cartInfoRepository, ArticleRepository articleRepository) {
        cartInfoRepository.deleteAll();
        articleRepository.deleteAll();

    }


}
