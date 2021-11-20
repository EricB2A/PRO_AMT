/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file User.java
 *
 * @brief Model representing the user
 */

package com.example.amt_demo.model;

import com.example.amt_demo.utils.login.UserCredentialsDTO;

import javax.persistence.*;

@Entity
public class User {
    @Id
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;
    private String role;

    /**
     * Default constructor of User
     */
    public User() {

    }

    /**
     * Constructor of User
     * @param id the id of the User
     * @param role the role of the User
     * @param cred the username of the User
     */
    public User(Integer id, String role, UserCredentialsDTO cred) {
        this.id = id ;
        this.username = cred.getUsername();
        this.role= role;
    }

    /**
     * Getter of the id
     * @return the id of the User
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter of the id of the User
     * @param id the id of the User
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter of the username of the User
     * @return the username of the User
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter of the name of the User
     * @param username the username of the User
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter of the role of the User
     * @return the role of the User
     */
    public String getRole() {
        return role;
    }

    /**
     * Setter of the role of the User
     * @param role the role of the User
     */
    public void setRole(String role) {
        this.role = role;
    }
}