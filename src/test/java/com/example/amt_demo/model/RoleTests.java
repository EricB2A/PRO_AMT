/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file RoleTests.java
 *
 * @brief Unit tests verifying the behavior of the model Role
 */

package com.example.amt_demo.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RoleTests {

    @Test
    public void RoleTests_getSetName() {
        Role role = new Role();
        String name = "Admin";
        role.setName(name);
        Assertions.assertEquals(name, role.getName());
    }
}