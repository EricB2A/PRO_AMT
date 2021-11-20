/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file AmtDemoApplicationTests.java
 *
 * @brief
 */

package com.example.amt_demo;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AmtDemoApplicationTests {

    @Autowired
    private MockMvc mvc;

    /**
     *
     */
    @Test
    void AmtDemoApplicationTests_success() {
        /* !!!! Mandatory for Git Action Workflow
         * Dummy run necessary to trigger entity framework creation of DB structure
         * Next steps will run sql scripts to populate db
         * */
        Assertions.assertEquals(1,1);
    }


    /**
     *
     */
    @Test
    public void AmtDemoApplication_helloWorld() {
        String hello = "Hello, World!";
        Assertions.assertEquals("Hello, World!", hello);
    }

    /**
     *
     */
    @Test
    void AmtDemoApplicationTests_contextLoads() {

    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void AmtDemoApplication_getHomepage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/home.jsp"));
    }
}