/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file UserCredentialsDTO.java
 *
 * @brief Class representing the userCredentials
 */

package com.example.amt_demo.utils.login;

public class UserCredentialsDTO {

    private final String username;
    private final String password;

    /**
     * Constructor
     * @param username Username
     * @param password Password
     */
    public UserCredentialsDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Getter of username
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter of password
     * @return The password
     */
    public String getPassword() {
        return password;
    }
}