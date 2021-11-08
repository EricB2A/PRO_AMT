package com.example.amt_demo.service;

import com.example.amt_demo.model.UserCredentials;
import org.springframework.web.reactive.function.client.WebClient;

public class LoginService {
    private final WebClient webclient;

    public LoginService(String url) {
        webclient = WebClient.create(url);
    }

    public boolean checkCredentials(UserCredentials credentials) {
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = webclient.post();
        WebClient.RequestBodySpec bodySpec = uriSpec.uri("/auth/login");
        StringBuilder body = new StringBuilder();


        return false; // TODO: check credentials
    }



}
