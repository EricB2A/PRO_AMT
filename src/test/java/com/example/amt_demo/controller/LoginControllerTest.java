package com.example.amt_demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void getLogin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/login.jsp"));
    }

    @Test
    @WithAnonymousUser
    public void getSignup() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/signup.jsp"));
    }

    @Test
    @WithAnonymousUser
    public void anonymousUserLogout() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/logout"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(roles = {"admin"})
    public void loggedUserLogout() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/logout").with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}


