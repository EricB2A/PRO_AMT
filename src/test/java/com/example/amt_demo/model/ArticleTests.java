/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file ArticleTests.java
 *
 * @brief Units test verifying the behavior of the model Article
 */

package com.example.amt_demo.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArticleTests {

    /**
     *
     */
    @Test
    public void ArticleTests_getSetName() {
        Article article = new Article();
        String name = "Alibaba";
        article.setName(name);
        Assertions.assertEquals(name, article.getName());
    }

    /**
     *
     */
    @Test
    public void ArticleTests_getSetDescription() {
        Article article = new Article();
        String desc = "An orient carpet";
        article.setDescription(desc);
        Assertions.assertEquals(desc, article.getDescription());
    }

    /**
     *
     */
    @Test
    public void ArticleTests_getSetPrice() {
        Article article = new Article();
        double price = 10000.00;
        article.setPrice(price);
        Assertions.assertEquals(price, article.getPrice());
    }

    /**
     *
     */
    @Test
    public void ArticleTests_getSetId() {
        Article article = new Article();
        int id = 10000;
        article.setId(id);
        Assertions.assertEquals(id, article.getId());
    }

    /**
     *
     */
    @Test
    public void ArticleTests_getSetQuantity() {
        Article article = new Article();
        int quantity = 42;
        article.setQuantity(quantity);
        Assertions.assertEquals(quantity, article.getQuantity());
    }
}