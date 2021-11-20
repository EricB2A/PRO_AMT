/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file ArticleRepositoryTests.java
 *
 * @brief
 */

package com.example.amt_demo.model;

import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleRepositoryTests {

    @Autowired
    private ArticleRepository articleRepository;

    /**
     *
     */
    @BeforeAll
    static void ArticleRepositoryTests_init() {
        System.out.println("ArticleRepositoryTests_init");
        Assertions.assertEquals(1,1);
    }

    /**
     *
     * @param articleRepository
     */
    @BeforeAll
    @Order(2)
    static void ArticleRepositoryTests_init2(@Autowired ArticleRepository articleRepository) {
        for(int i = 1; i <= 10; i++) {
            Article article = articleRepository.save(new Article("test name " + i, "test desc " + i, i * 10.00));
        }
    }

    @Test
    void ArticleRepositoryTests_contextLoads() {
    }

    /**
     *
     */
    @Test
    void ArticleRepositoryTests_firstCarpetExistsInDB(){
        Article test = articleRepository.findByName("test name 1");
        Assertions.assertEquals("test name 1", test.getName());
    }

    /**
     *
     */
    @Test
    void ArticleRepositoryTests_correctDataOfSecondCarpetFromRepository(){
        Article test = articleRepository.findByName("test name 2");

        Assertions.assertEquals("test name 2", test.getName());
        Assertions.assertEquals("test desc 2", test.getDescription());
        Assertions.assertEquals(20.00, test.getPrice());
    }

    /**
     *
     */
    @Test
    void ArticleRepositoryTests_correctTotalPrice(){
        Article test = articleRepository.findByName("test name 4");
        Article test2 = articleRepository.findByName("test name 9");

        Assertions.assertEquals(130, test.getPrice() + test2.getPrice());
    }

    /**
     *
     */
    @Test
    public void ArticleRepositoryTests_correctDescription(){
        Article test = articleRepository.findByName("test name 6");
        Assertions.assertEquals("test desc 6", test.getDescription());
    }

    /**
     *
     */
    @Test
    public void ArticleRepositoryTests_countMockElement() {
        Assertions.assertEquals(10, articleRepository.count());
    }

    /**
     *
     * @param articleRepository
     */
    @AfterAll
    static void ArticleRepositoryTests_cleanUp(@Autowired ArticleRepository articleRepository) {
        articleRepository.deleteAll();
    }
}