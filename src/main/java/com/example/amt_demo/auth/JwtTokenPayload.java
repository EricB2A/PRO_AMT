/**
 * @team AMT - Silkyroad
 * @authors Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file JwtTokenPayload.java
 *
 * @brief TODO
 */

package com.example.amt_demo.auth;

public class JwtTokenPayload {
    public static final String USERNAME = "sub";
    public static final String ROLE = "role";

    private String username;
    private String role;

    /**
     * Constructor of JwtTokenPayload
     * @param username the username
     * @param role the role
     */
    public JwtTokenPayload(String username, String role) {
        this.username = username;
        this.role = role;
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

    /**
     * Getter of the role
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Setter of the role
     * @param role - the role
     */
    public void setRole(String role) {
        this.role = role;
    }
}