/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CustomUserDetails.java
 *
 * @brief TODO
 */

package com.example.amt_demo.service;
import org.springframework.stereotype.Service;

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