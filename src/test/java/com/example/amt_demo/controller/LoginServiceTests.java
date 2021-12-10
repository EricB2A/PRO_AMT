/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file LoginServiceTests.java
 * @brief
 */

package com.example.amt_demo.controller;

import com.example.amt_demo.service.LoginService;
import com.example.amt_demo.utils.login.UserCredentialsDTO;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class LoginServiceTests {

    public static MockWebServer mockLogin;

    public static LoginService loginService;

    /**
     * @throws IOException
     */
    @BeforeAll
    public static void setup() throws IOException {
        mockLogin = new MockWebServer();
        mockLogin.start();
        loginService = new LoginService(String.format("http://localhost:%s", mockLogin.getPort()));
    }


    /**
     * @throws IOException
     */
    @AfterAll
    public static void tearDown() throws IOException {
        mockLogin.shutdown();
    }

    /**
     * @throws InterruptedException
     * @throws JSONException
     */
    @Test
    void correctCredentials() throws InterruptedException, JSONException {
        UserCredentialsDTO credentials = new UserCredentialsDTO("username", "password");
        JSONObject json = new JSONObject();
        json.put("token", "00000000")
                .put("account", new JSONObject()
                        .put("id", 0)
                        .put("username", "username")
                        .put("role", "admin"));
        mockLogin.enqueue(new MockResponse()
                .setBody(json.toString()));
        Assertions.assertEquals(loginService.login(credentials).getStatusCode(), 200);
        RecordedRequest recordedRequest = mockLogin.takeRequest();
        Assertions.assertEquals("POST", recordedRequest.getMethod());
        Assertions.assertEquals("/auth/login", recordedRequest.getPath());
    }

    /**
     * @throws InterruptedException
     * @throws JSONException
     */
    @Test
    public void validRegister() throws InterruptedException, JSONException {
        //Mockito.when(userRepository.save(any(User.class))).thenReturn(new User(0, "user", "username"));
        //loginService = new LoginService(String.format("http://localhost:%s", mockLogin.getPort()), userRepository);
        UserCredentialsDTO credentials = new UserCredentialsDTO("username", "password");
        JSONObject credentialsJson = new JSONObject()
                .put("id", "0")
                .put("username", "username")
                .put("role", "user");
        mockLogin.enqueue(new MockResponse()
                .setResponseCode(201)
                .setBody(credentialsJson.toString()));
        Assertions.assertEquals(loginService.registerUser(credentials).getStatusCode(), 201);
        RecordedRequest recordedRequest = mockLogin.takeRequest();
        Assertions.assertEquals("POST", recordedRequest.getMethod());
        Assertions.assertEquals("/accounts/register", recordedRequest.getPath());
    }


    /**
     * @throws InterruptedException
     * @throws JSONException
     */
    @Test
    public void wrongCredentials() throws InterruptedException, JSONException {
        UserCredentialsDTO credentials = new UserCredentialsDTO("username", "password");
        JSONObject error = new JSONObject()
                .put("error", "testError");
        mockLogin.enqueue(new MockResponse()
                .setResponseCode(403)
                .setBody(error.toString()));
        Assertions.assertEquals(loginService.login(credentials).getStatusCode(), 403);
        RecordedRequest recordedRequest = mockLogin.takeRequest();
        Assertions.assertEquals("POST", recordedRequest.getMethod());
        Assertions.assertEquals("/auth/login", recordedRequest.getPath());
    }

    /**
     * @throws InterruptedException
     * @throws JSONException
     */
    @Test
    public void invalidRegister409() throws HttpClientErrorException.Conflict {

        Assertions.assertThrows(HttpClientErrorException.Conflict.class, () -> {
            UserCredentialsDTO credentials = new UserCredentialsDTO("username", "password");
            JSONObject credentialsJson = new JSONObject()
                    .put("error", "testError");
            mockLogin.enqueue(new MockResponse()
                    .setResponseCode(409)
                    .setBody(credentialsJson.toString()));
            Assertions.assertEquals(loginService.registerUser(credentials).getStatusCode(), 409);
            RecordedRequest recordedRequest = mockLogin.takeRequest();
            Assertions.assertEquals("POST", recordedRequest.getMethod());
            Assertions.assertEquals("/accounts/register", recordedRequest.getPath());
        });

    }

    /**
     * @throws InterruptedException
     * @throws JSONException
     */
    @Test
    public void invalidRegister422()  {
        Assertions.assertThrows(HttpClientErrorException.UnprocessableEntity.class, () -> {
            UserCredentialsDTO credentials = new UserCredentialsDTO("username", "password");
            JSONObject credentialsJson = new JSONObject()
                    .put("error", "testError");
            mockLogin.enqueue(new MockResponse()
                    .setResponseCode(422)
                    .setBody(credentialsJson.toString()));
            Assertions.assertEquals(loginService.registerUser(credentials).getStatusCode(), 422);
            RecordedRequest recordedRequest = mockLogin.takeRequest();
            Assertions.assertEquals("POST", recordedRequest.getMethod());
            Assertions.assertEquals("/accounts/register", recordedRequest.getPath());
        });
    }
}