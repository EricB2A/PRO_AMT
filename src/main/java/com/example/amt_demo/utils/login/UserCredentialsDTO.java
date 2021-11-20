/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file UserCredentialsDTO.java
 *
 * @brief TODO
 */

package com.example.amt_demo.utils.login;

public class UserCredentialsDTO {

    private String username;
    private String password;

    /**
     *
     * @param username
     * @param password
     */
    public UserCredentialsDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }
}