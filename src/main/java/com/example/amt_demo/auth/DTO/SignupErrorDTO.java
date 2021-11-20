/**
 * @team AMT - Silkyroad
 * @authors Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file SignupErrorDTO.java
 *
 * @brief TODO
 */

package com.example.amt_demo.auth.DTO;

public class SignupErrorDTO {
    private String property;
    private String message;

    /**
     * Getter of the property
     * @return the property
     */
    public String getProperty() {
        return property;
    }

    /**
     * Setter of the property
     * @param property the property
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * Getter of the message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter of the message
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}