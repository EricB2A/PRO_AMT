package com.example.amt_demo.exception;

import org.springframework.security.core.AuthenticationException;

public class AutentificationException extends AuthenticationException {

    public AutentificationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AutentificationException(String msg) {
        super(msg);
    }
}
