package com.example.amt_demo.model;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class CarpetRepositoryTests {
    @Autowired
    private CarpetRepository carpetRepository;

    @BeforeAll
    static void CarpetRepositoryTests_init(@Autowired CarpetRepository carpetRepository) {
        for(int i = 1; i <= 10; i++) {
            carpetRepository.save(new Carpet(i, "test name " + i, "test desc " + i, i * 10.00));
        }
    }

    @Test
    void CarpetRepositoryTests_contextLoads() {
    }

    @Test
    void CarpetRepositoryTests_firstCarpetExistsInDB(){
        Carpet test = carpetRepository.findByName("test name 1");

        Assertions.assertEquals("test name 1", test.getName());
    }

    @Test
    void CarpetRepositoryTests_correctDataOfSecondCarpetFromRepository(){
        Carpet test = carpetRepository.findByName("test name 2");

        Assertions.assertEquals("test name 2", test.getName());
        Assertions.assertEquals("test desc 2", test.getDescription());
        Assertions.assertEquals(20.00, test.getPrice());
    }

    @Test
    void CarpetRepositoryTests_correctTotalPrice(){
        Carpet test = carpetRepository.findByName("test name 4");
        Carpet test2 = carpetRepository.findByName("test name 9");

        Assertions.assertEquals(130, test.getPrice() + test2.getPrice());
    }

    @Test
    public void CarpetRepositoryTests_correctDescription(){
        Carpet test = carpetRepository.findByName("test name 6");
        Assertions.assertEquals("test desc 6", test.getDescription());
    }

    @Test
    public void CarpetRepositoryTests_countMockElement() {
        long count = carpetRepository.count();
        Assertions.assertEquals(10, count);
    }

    @AfterAll
    static void CarpetRepositoryTests_cleanUp(@Autowired CarpetRepository carpetRepository) {
        carpetRepository.deleteAll();

    }
}
