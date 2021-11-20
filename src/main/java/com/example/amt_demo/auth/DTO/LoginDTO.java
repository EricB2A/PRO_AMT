/**
 * @team AMT - Silkyroad
 * @authors Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file LoginDTO.java
 *
 * @brief TODO
 */

package com.example.amt_demo.auth.DTO;

public class LoginDTO {
    private String username;
    private String password;

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

    /**
     * Getter of the password
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter of the password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method toString() formatting the attributes of the class LoginDTIO
     * @return a formatted string with the username and the password
     */
    @Override
    public String toString() {
        return "LoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}