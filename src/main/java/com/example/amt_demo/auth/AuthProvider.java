/**
 * @team AMT - Silkyroad
 * @authors Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file AuthProvider.java
 *
 * @brief TODO
 */

package com.example.amt_demo.auth;

import com.example.amt_demo.exception.AutentificationException;
import com.example.amt_demo.model.User;
import com.example.amt_demo.model.UserRepository;
import com.example.amt_demo.service.CustomUserDetails;
import com.example.amt_demo.service.CustomUserDetailsService;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//COMMENTAIRE
@Service
public class AuthProvider implements AuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;
    final private CustomUserDetailsService userService;
    private final LoginService loginService;

    /**
     *
     * @param userRepository
     * @param loginService
     * @param userRepo
     */
    @Autowired
    public AuthProvider(CustomUserDetailsService userRepository, LoginService loginService, UserRepository userRepo) {
        this.loginService = loginService;
        this.userService = userRepository;
        this.userRepository = userRepo;
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
            LoginAPIResponse response = loginService.login(new UserCredentialsDTO(username, password));

            if (response.getStatusCode() == 200) {
                JSONObject jsonRes = response.getContent();
                String token = jsonRes.getString("token");
                JSONObject account = jsonRes.getJSONObject("account");

                String role = account.getString("role");
                try{
                    UserDetails userDetails = userService.loadUserByUsername(authentication.getPrincipal().toString());
                    return new UsernameJwtAuthenticationToken(userDetails, password, token, AuthorityUtils.createAuthorityList(CustomUserDetails.ROLE_PREFIX + role));
                } catch (UsernameNotFoundException e){
                    UserCredentialsDTO credentials = new UserCredentialsDTO(username, password);
                    User user = new User(account.getInt("id"), account.getString("role"), credentials);
                    userRepository.save(user);
                    UserDetails userDetails = userService.loadUserByUsername(authentication.getPrincipal().toString());
                    return new UsernameJwtAuthenticationToken(userDetails, password, token, AuthorityUtils.createAuthorityList(CustomUserDetails.ROLE_PREFIX + role));
                }
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
}
