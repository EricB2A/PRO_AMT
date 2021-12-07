/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CustomUserDetails.java
 *
 * @brief TODO
 */

package com.example.amt_demo.service;

import com.example.amt_demo.auth.UsernameJwtAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CustomUserService {


    private CustomUserDetails user;


    public void setUser(CustomUserDetails user){
        this.user = user;
    }

    public CustomUserDetails getUser(){
        return user;
    }



}