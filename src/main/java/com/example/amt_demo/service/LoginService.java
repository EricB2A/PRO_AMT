/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file LoginService.java
 *
 * @brief TODO
 */

package com.example.amt_demo.service;

import com.example.amt_demo.utils.login.LoginAPIResponse;
import com.example.amt_demo.utils.login.UserCredentialsDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Service
public class LoginService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final WebClient webclient;

    /**
     *
     * @param url The url of the authentication service
     */
    @Autowired
    public LoginService(@Value("${com.example.amt_demo.config.authservice.url}") String url) {
        webclient = WebClient.create(url);
    }

    /**
     *
     * @param credentials THe user credentials
     * @return A LoginAPIResponse containing the response
     * @throws JSONException
     */
    public LoginAPIResponse registerUser(UserCredentialsDTO credentials) throws JSONException {
        logger.info("register user"+ credentials);
        JSONObject json = new JSONObject()
                .put("password", credentials.getPassword())
                .put("username", credentials.getUsername());
        ResponseEntity<String> response = webclient
                .post()
                .uri("/accounts/register")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(json.toString()))
                .retrieve()
                //Prevents error throwing
                .onStatus(
                        status -> status.value() == 409 || status.value() == 422,
                        clientResponse -> Mono.empty()
                )
                .toEntity(String.class)
                .block();
        JSONObject responseBodyJSON = new JSONObject(response.getBody());

        if (response.getStatusCode() == HttpStatus.CREATED) {

        } else {
            throw HttpClientErrorException.create(response.getStatusCode(), "", response.getHeaders(), response.getBody().getBytes(StandardCharsets.UTF_8), Charset.defaultCharset());
        }
        return new LoginAPIResponse(responseBodyJSON, response.getStatusCodeValue(), LoginAPIResponse.RequestType.REGISTER);
    }

    /**
     *
     * @param credentials
     * @return
     * @throws JSONException
     */
    public LoginAPIResponse login(UserCredentialsDTO credentials) throws JSONException {
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
        return new LoginAPIResponse(responseBodyJSON, response.getStatusCodeValue(), LoginAPIResponse.RequestType.LOGIN);
    }

    /**
     *
     */
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(
                new AnonymousAuthenticationToken("ANONYMOUS", "ANONYMOUS", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")));
    }
}