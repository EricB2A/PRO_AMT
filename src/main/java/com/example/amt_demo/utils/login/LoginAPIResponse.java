/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file LoginAPIResponse.java
 *
 * @brief TODO
 */

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

    /**
     * Constructor of LoginAPIResponse
     * @param content
     * @param statusCode
     * @param requestType
     */
    public LoginAPIResponse(JSONObject content, int statusCode, RequestType requestType) {
        this.content = content;
        this.statusCode = statusCode;
        this.requestType = requestType;
    }

    /**
     * Getter of content
     * @return the content
     */
    public JSONObject getContent() {
        return content;
    }

    /**
     * Getter of the status code
     * @return the status code
     */
    public int getStatusCode() {
        return statusCode;
    }
}