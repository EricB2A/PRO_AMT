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

public class CartInfoTests {

    /**
     * Testing if the getter & the setter of the id work properly
     */
    @Test
    void CartInfoTest_getSetId() {
        CartInfo cartInfo = new CartInfo();
        int id = 10;
        cartInfo.setId(id);
        Assertions.assertEquals(id, cartInfo.getId());
    }

    /**
     * Testing if the getter & the setter of the Article work properly
     */
    @Test
    void CartInfoTest_getSetArticle() {
        CartInfo cartInfo = new CartInfo();
        Article article = new Article();
        cartInfo.setArticle(article);
        Assertions.assertEquals(article, cartInfo.getArticle());
    }

    /**
     * Testing if the getter & the setter of the quantity work properly
     */
    @Test
    void CartInfoTest_getSetQuantity() {
        CartInfo cartInfo = new CartInfo();
        int quantity = 42;
        cartInfo.setQuantity(quantity);
        Assertions.assertEquals(quantity, cartInfo.getQuantity());
    }

    /**
     * Testing if the getter & the setter of the User work properly
     */
    @Test
    void CartInfoTest_getSetUser() {
        CartInfo cartInfo = new CartInfo();
        User user = new User();
        cartInfo.setUser(user);
        Assertions.assertEquals(user, cartInfo.getUser());
    }
}