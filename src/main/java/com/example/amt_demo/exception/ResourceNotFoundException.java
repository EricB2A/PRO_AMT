package com.example.amt_demo.exception;

import org.springframework.security.core.AuthenticationException;


public class ResourceNotFoundException extends AuthenticationException {
    public ResourceNotFoundException(String msg) {
        super(msg);
    }
    public ResourceNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

