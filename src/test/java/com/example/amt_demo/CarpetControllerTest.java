package com.example.amt_demo;


import com.example.amt_demo.model.Carpet;
import com.example.amt_demo.model.CarpetRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureMockMvc
public class CarpetControllerTest {
    @MockBean
    CarpetRepository repository;

    @Test
    public void testCreateCarpet() {
        Carpet carpet = new Carpet(1, "testCarpet", "testDesc", 10.0);

    }

    @Test
    public void testDatabase() {
        Assertions.assertEquals(0, repository.count());
        Assertions.assertEquals(1, repository.count());
    }

}
