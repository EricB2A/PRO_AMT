package com.example.amt_demo;

import com.example.amt_demo.model.UserCredentials;
import com.example.amt_demo.service.LoginService;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;

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
    void assertFalse() {
        UserCredentials credentials = new UserCredentials("username", "password");
        Assertions.assertFalse(loginService.checkCredentials(credentials));
    }





    @AfterAll
    static void tearDown() throws IOException {
        mockLogin.shutdown();
    }


}
