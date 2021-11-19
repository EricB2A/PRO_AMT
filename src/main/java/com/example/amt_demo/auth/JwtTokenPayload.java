package com.example.amt_demo.auth;

public class JwtTokenPayload {
    public static final String USERNAME = "sub";
    public static final String ROLE = "role";

    private String username;
    private String role;

    public JwtTokenPayload(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
