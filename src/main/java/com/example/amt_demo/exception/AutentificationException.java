/**
 * @team AMT - Silkyroad
 * @authors Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file AutentificationException.java
 *
 * @brief TODO
 */

package com.example.amt_demo.exception;

import org.springframework.security.core.AuthenticationException;

public class AutentificationException extends AuthenticationException {

    /**
     *
     * @param msg
     * @param cause
     */
    public AutentificationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     *
     * @param msg
     */
    public AutentificationException(String msg) {
        super(msg);
    }
}
