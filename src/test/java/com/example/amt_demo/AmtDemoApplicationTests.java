package com.example.amt_demo;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
@SpringBootTest
@AutoConfigureMockMvc
class AmtDemoApplicationTests {

    @Autowired
    private MockMvc mvc;


    @Test
    void contextLoads() {
    }

    @Test
    public void getHello() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    public void AmtDemoApplication_helloWorld() {
        String hello = "Hello, World!";
        Assertions.assertEquals("Hello, World!", hello);
    }

    @Test
    public void AmtDemoApplication_findMax() {
        Assertions.assertEquals(94, AmtDemoApplication.getMax(new int[]{1,53,94,13,37,42}));
    }


}
