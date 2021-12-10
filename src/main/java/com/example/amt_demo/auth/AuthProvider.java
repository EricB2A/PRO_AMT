/**
 * @team AMT - Silkyroad
 * @authors Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file AuthProvider.java
 *
 * @brief TODO
 */

package com.example.amt_demo.auth;

import com.example.amt_demo.exception.AutentificationException;
import com.example.amt_demo.service.CustomUserDetails;
import org.springframework.security.core.userdetails.User;
import com.example.amt_demo.service.CustomUserService;
import com.example.amt_demo.service.LoginService;
import com.example.amt_demo.utils.login.LoginAPIResponse;
import com.example.amt_demo.utils.login.UserCredentialsDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

//COMMENTAIRE
@Service
public class AuthProvider implements AuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LoginService loginService;
    private final CustomUserService userService;

    /**
     *
     * @param loginService
     * @param userService
     */
    @Autowired
    public AuthProvider(LoginService loginService, CustomUserService userService) {
        this.loginService = loginService;
        this.userService = userService;
    }

    /**
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        try {
            //Perform request to the authentication service
            LoginAPIResponse response = loginService.login(new UserCredentialsDTO(username, password));

            //If authentified
            if (response.getStatusCode() == 200) {
                JSONObject jsonRes = response.getContent();
                String token = jsonRes.getString("token");
                JSONObject account = jsonRes.getJSONObject("account");

                int id = Integer.parseInt(account.getString("id"));
                String role = account.getString("role");
                //Try to find the user in local user database
                CustomUserDetails user = new CustomUserDetails(id, username, AuthorityUtils.createAuthorityList(CustomUserDetails.ROLE_PREFIX + role));
                this.userService.setUser(user);
                return new UsernameJwtAuthenticationToken(user, password, token, AuthorityUtils.createAuthorityList(CustomUserDetails.ROLE_PREFIX + role));
            }
        } catch (JSONException e) {
            // Ignore
        }
        throw new AutentificationException("Bad credential");
    }

    /**
     *
     * @param auth
     * @return
     */
    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }

    public void addUser(User user) throws AutentificationException {}
}
