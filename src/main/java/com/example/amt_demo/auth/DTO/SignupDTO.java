/**
 * @team AMT - Silkyroad
 * @authors Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file SignupDTO.java
 *
 * @brief TODO
 */

package com.example.amt_demo.auth.DTO;

public class SignupDTO {
    private String email;
    private String password;
    private String username;

    /**
     * Getter of the email
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter of the email
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter of the password
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter of the password
     * @param password the  password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter of the username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter of the username
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }
}