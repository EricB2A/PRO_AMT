package com.example.amt_demo.auth.DTO;

public class SignupErrorDTO {
    private String property;
    private String message;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
