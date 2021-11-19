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
    public User() {

    }

    public User(Integer id, String role, UserCredentialsDTO cred) {
        this.id = id ;
        this.username = cred.getUsername();
        this.role= role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}