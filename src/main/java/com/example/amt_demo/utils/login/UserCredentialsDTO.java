package com.example.amt_demo.utils.login;

public class UserCredentialsDTO {
    private String username;
    private String password;
    public UserCredentialsDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
