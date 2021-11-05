package com.example.amt_demo.service;

import org.springframework.web.reactive.function.client.WebClient;

public class LoginService {
    private final WebClient webclient;

    public LoginService(String url) {
        webclient = WebClient.create(url);
    }

    public boolean checkCredentials(String something) {
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = webclient.post();
        WebClient.RequestBodySpec bodySpec = uriSpec.uri("/auth/login");

        return false; // TODO: check credentials
    }



}
