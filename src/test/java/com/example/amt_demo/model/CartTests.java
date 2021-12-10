/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CartInfoTests.java
 *
 * @brief Units test verifying the behavior of the model CartInfo
 */

package com.example.amt_demo.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CartTests {

    /**
     * Testing if the getter & the setter of the id work properly
     */
    @Test
    void CartInfoTest_getSetId() {
        Cart cart = new Cart();
        int id = 10;
        cart.setId(id);
        Assertions.assertEquals(id, cart.getId());
    }

    /**
     * Testing if the getter & the setter of the Article work properly
     */
    @Test
    void CartInfoTest_getSetArticle() {
        Cart cart = new Cart();
        Article article = new Article();
        cart.setArticle(article);
        Assertions.assertEquals(article, cart.getArticle());
    }

    /**
     * Testing if the getter & the setter of the quantity work properly
     */
    @Test
    void CartInfoTest_getSetQuantity() {
        Cart cart = new Cart();
        int quantity = 42;
        cart.setQuantity(quantity);
        Assertions.assertEquals(quantity, cart.getQuantity());
    }

    /**
     * Testing if the getter & the setter of the User work properly
     */
    @Test
    void CartInfoTest_getSetUser() {
        Cart cart = new Cart();
       // User user = new User();
        //cart.setUser(user);
        //Assertions.assertEquals(user, cart.getUser());
    }
}