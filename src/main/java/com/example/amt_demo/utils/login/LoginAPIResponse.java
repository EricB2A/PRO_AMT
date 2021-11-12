package com.example.amt_demo.utils.login;

import org.json.JSONObject;

public class LoginAPIResponse {
    private final JSONObject content;
    private final int statusCode;

    public LoginAPIResponse(JSONObject content, int statusCode) {
        this.content = content;
        this.statusCode = statusCode;
    }

    public JSONObject getContent() {
        return content;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
