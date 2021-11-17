package com.example.amt_demo.auth;

import com.example.amt_demo.service.CustomUserDetails;
import com.example.amt_demo.service.CustomUserDetailsService;
import com.example.amt_demo.service.CustomUserDetailsServiceImpl;
import com.example.amt_demo.service.LoginService;
import com.example.amt_demo.utils.login.LoginAPIResponse;
import com.example.amt_demo.utils.login.UserCredentials;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthProvider implements AuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static String jwtAuthserviceURL;

    final private CustomUserDetailsService userService;

    @Autowired
    public AuthProvider(CustomUserDetailsService userRepository, @Value("${com.example.amt_demo.config.authservice.url}") String  authServiceUrl ) {
        logger.info(authServiceUrl);
        this.userService = userRepository;
        this.jwtAuthserviceURL = authServiceUrl;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        LoginService ls = new LoginService(jwtAuthserviceURL);
        try {
            LoginAPIResponse response = ls.login(new UserCredentials(username, password));
            if (response.getStatusCode() == 200) {
                JSONObject jsonRes = response.getContent();
                String token = jsonRes.getString("token");
                JSONObject account = jsonRes.getJSONObject("account");
                String role = account.getString("role");
                UserDetails userDetails = userService.loadUserByUsername(authentication.getPrincipal().toString());
                return new UsernameJwtAuthenticationToken(userDetails, password, token, AuthorityUtils.createAuthorityList(CustomUserDetails.ROLE_PREFIX + role));
            }
        } catch (JSONException e) {
            // Ignore
        }
        return null;

    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
