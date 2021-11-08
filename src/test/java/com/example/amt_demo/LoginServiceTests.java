package com.example.amt_demo;

import com.example.amt_demo.model.UserCredentials;
import com.example.amt_demo.service.LoginService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.io.IOException;


public class LoginServiceTests {

    public static MockWebServer mockLogin;
    public static LoginService loginService;

    @BeforeAll
    static void setup() throws IOException {
        mockLogin = new MockWebServer();
        mockLogin.start();
        String url = String.format("http://localhost:%s", mockLogin.getPort());
        loginService = new LoginService(url);
    }

    @Test
    void correctCredentials() {
        UserCredentials credentials = new UserCredentials("username", "password");
        JSONObject json = new JSONObject();
        json.put("token", "00000000")
                .put("account", new JSONObject()
                        .put("id", 0)
                        .put("username", "username")
                        .put("role", "admin"));
        mockLogin.enqueue(new MockResponse()
                .setBody(json.toString()));
        Assertions.assertTrue(loginService.checkCredentials(credentials));
    }

    @Test
    void wrongCredentials() {
        UserCredentials credentials = new UserCredentials("username", "password");
        JSONObject error = new JSONObject()
                .put("error", "testError");
        mockLogin.enqueue(new MockResponse()
                .setResponseCode(403)
                .setBody(error.toString()));
        Assertions.assertFalse(loginService.checkCredentials(credentials));
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockLogin.shutdown();
    }


}
