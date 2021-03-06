/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file HomeControllerTests.java
 *
 * @brief
 */

package com.example.amt_demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void HomeControllerTests_contextLoads() {
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void HomeControllerTests_getIndex() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/accueil").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }
}