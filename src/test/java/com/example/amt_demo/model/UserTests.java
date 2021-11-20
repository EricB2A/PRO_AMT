/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file UserTests.java
 *
 * @brief Units test verifying the behavior of the model User
 */

package com.example.amt_demo.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTests {

    @Test
    public void UserTests_getSetId() {
        User user = new User();
        int id = 1;
        user.setId(id);
        Assertions.assertEquals(id, user.getId());
    }

    @Test
    public void UserTests_getSetUsername() {
        User user = new User();
        String username = "Shaggy";
        user.setUsername(username);
        Assertions.assertEquals(username, user.getUsername());
    }

    @Test
    public void UserTests_getSetRole() {
        User user = new User();
        String role = "Admin";
        user.setRole(role);
        Assertions.assertEquals(role, user.getRole());
    }
}