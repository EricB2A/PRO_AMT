package com.example.amt_demo;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AmtDemoApplicationTests {
    @Autowired
    private MockMvc mvc;

    @Test
    void AmtDemoApplicationTests_success() {
        /* !!!! Mandatory for Git Action Workflow
         * Dummy run necessary to trigger entity framework creation of DB structure
         * Next steps will run sql scripts to populate db
         * */
        Assertions.assertEquals(1,1);
    }
    @Test
    void AmtDemoApplicationTests_contextLoads() {
    }

    @Test
    public void AmtDemoApplication_getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    public void AmtDemoApplication_helloWorld() {
        String hello = "Hello, World!";
        Assertions.assertEquals("Hello, World!", hello);
    }
}