/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file LoginServiceTests.java
 *
 * @brief
 */

package com.example.amt_demo.controller;

import com.example.amt_demo.model.UserRepository;
import com.example.amt_demo.utils.login.UserCredentialsDTO;
import com.example.amt_demo.service.LoginService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.io.IOException;

@AutoConfigureMockMvc
public class LoginServiceTests {

    public static MockWebServer mockLogin;
    public static LoginService loginService;
    public static UserRepository userRepository;

    /**
     *
     * @throws IOException
     */
    @BeforeAll
    @Autowired
    static void setup() throws IOException {
        mockLogin = new MockWebServer();
        mockLogin.start();
        String url = String.format("http://localhost:%s", mockLogin.getPort());
        loginService = new LoginService(url, userRepository);
    }

    /**
     *
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
     *
     * @throws InterruptedException
     * @throws JSONException
     */
    @Test
    void wrongCredentials() throws InterruptedException, JSONException {
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
     *
     * @throws InterruptedException
     * @throws JSONException
     */
    //TODO:FIX THIS TEST
    /*
    @Test
    public void validRegister() throws InterruptedException, JSONException {
        UserCredentialsDTO credentials = new UserCredentialsDTO("username","password");
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
    */


    /**
     *
     * @throws InterruptedException
     * @throws JSONException
     */
    @Test
    public void invalidRegister409() throws InterruptedException, JSONException {
        UserCredentialsDTO credentials = new UserCredentialsDTO("username","password");
        JSONObject credentialsJson = new JSONObject()
                .put("error", "testError");
        mockLogin.enqueue(new MockResponse()
                .setResponseCode(409)
                .setBody(credentialsJson.toString()));
        Assertions.assertEquals(loginService.registerUser(credentials).getStatusCode(), 409);
        RecordedRequest recordedRequest = mockLogin.takeRequest();
        Assertions.assertEquals("POST", recordedRequest.getMethod());
        Assertions.assertEquals("/accounts/register", recordedRequest.getPath());
    }

    /**
     *
     * @throws InterruptedException
     * @throws JSONException
     */
    @Test
    public void invalidRegister422() throws InterruptedException, JSONException {
        UserCredentialsDTO credentials = new UserCredentialsDTO("username","password");
        JSONObject credentialsJson = new JSONObject()
                .put("error", "testError");
        mockLogin.enqueue(new MockResponse()
                .setResponseCode(422)
                .setBody(credentialsJson.toString()));
        Assertions.assertEquals(loginService.registerUser(credentials).getStatusCode(), 422);
        RecordedRequest recordedRequest = mockLogin.takeRequest();
        Assertions.assertEquals("POST", recordedRequest.getMethod());
        Assertions.assertEquals("/accounts/register", recordedRequest.getPath());
    }

    /**
     *
     * @throws IOException
     */
    @AfterAll
    static void tearDown() throws IOException {
        mockLogin.shutdown();
    }
}