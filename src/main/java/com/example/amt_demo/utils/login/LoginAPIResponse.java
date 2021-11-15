package com.example.amt_demo.utils.login;

import org.json.JSONObject;

public class LoginAPIResponse {
    public enum RequestType {
        LOGIN,
        REGISTER
    }
    private final JSONObject content;
    private final int statusCode;
    private RequestType requestType;

    public LoginAPIResponse(JSONObject content, int statusCode, RequestType requestType) {
        this.content = content;
        this.statusCode = statusCode;
        this.requestType = requestType;
    }

    public JSONObject getContent() {
        return content;
    }

    public int getStatusCode() {
        return statusCode;
    }


}
