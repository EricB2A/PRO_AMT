/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CategoryTests.java
 *
 * @brief Units test verifying the behavior of the model Category
 */

package com.example.amt_demo.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTests {

    /**
     * Testing the correct behavior of the constructor
     */
    @Test
    public void Category_ConstructorTest() {
        String name = "Turkish";
        Category cat1 = new Category(name);
        Assertions.assertEquals(name, cat1.getName());
    }

    /**
     * Testing the correct behavior of the getter & the setter of the name of the Category
     */
    @Test
    public void Category_getSetName() {
        Category category = new Category();
        String name = "Orient";
        category.setName(name);
        Assertions.assertEquals(name, category.getName());
    }
}