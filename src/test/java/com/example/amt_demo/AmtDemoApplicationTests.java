package com.example.amt_demo;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.amt_demo.model.Carpet;
import com.example.amt_demo.model.CarpetRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class AmtDemoApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CarpetRepository carpetRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    void success() {
        /* !!!! Mandatory for Git Action Workflow
        * Dummy run necessary to trigger entity framework creation of DB structure
        * Next steps will run sql scripts to populate db
        * */
        Assertions.assertEquals(1,1);
    }

    @Test
    void firstCarpetExistsInDB(){
        Optional<Carpet> test = carpetRepository.findById(1);
        Assertions.assertEquals("test name", test.get().getName());
    }

    @Test
    public void AmtDemoApplication_helloWorld() {
        String hello = "Hello, World!";
        Assertions.assertEquals("Hello, World!", hello);
    }
}
