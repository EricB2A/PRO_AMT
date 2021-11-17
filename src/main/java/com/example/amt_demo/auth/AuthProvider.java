package com.example.amt_demo.auth;


import com.example.amt_demo.service.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthProvider implements AuthenticationProvider {

    final private CustomUserDetailsService userService;

    public AuthProvider(CustomUserDetailsService userRepository) {
        this.userService = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String password = authentication.getCredentials().toString();

    /* LoginService ls = new LoginService("http://fake.toke");
     try {
            LoginAPIResponse response = ls.login(new UserCredentials(username, password));
           if (response.getStatusCode() == 200) {
               JSONObject jsonRes = response.getContent();
                String token = jsonRes.getString("token");
                JSONObject account = jsonRes.getJSONObject("account");
                int id = account.getInt("id");
                String role = account.getString("role");*/

        JwtUtil jwt = new JwtUtil();
        UserDetails userDetails = userService.loadUserByUsername(authentication.getPrincipal().toString());
        String token = jwt.generateToken(userDetails);
        return new UsernameJwtAuthenticationToken(userDetails, password,token, AuthorityUtils.createAuthorityList("ROLE_admin"));
    /*         }

     } catch (JSONException e) {
            // Ignore
        }
        return null;
        */
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
