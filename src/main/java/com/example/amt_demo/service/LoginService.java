package com.example.amt_demo.service;

import com.example.amt_demo.model.UserCredentials;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class LoginService {
    private final WebClient webclient;

    public LoginService(String url) {
        webclient = WebClient.create(url);
    }

    public boolean registerUser(UserCredentials credentials) {
        JSONObject json = new JSONObject()
                .put("password", credentials.getPassword())
                .put("username", credentials.getUsername());
        ResponseEntity<String> response = webclient
                .post()
                .uri("/accounts/register")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(json.toString()))
                .retrieve()
                .onStatus(
                        status -> status.value() == 409 || status.value() == 422,
                        clientResponse -> Mono.empty()
                )
                .toEntity(String.class)
                .block();

        JSONObject responseBodyJSON = new JSONObject(response.getBody());
        System.out.println(responseBodyJSON);
        return response.getStatusCodeValue() == 201;
    }

    public boolean checkCredentials(UserCredentials credentials) {
        JSONObject json = new JSONObject()
                .put("password", credentials.getPassword())
                .put("username", credentials.getUsername());

        ResponseEntity<String> response = webclient
                .post()
                .uri("/auth/login")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(json.toString()))
                .retrieve()
                .onStatus(
                        status -> status.value() == 403,
                        clientResponse -> Mono.empty()
                )
                .toEntity(String.class)
                .block();

        JSONObject responseBodyJSON = new JSONObject(response.getBody());
        System.out.println(responseBodyJSON);
        return response.getStatusCodeValue() == 200;
    }



}
