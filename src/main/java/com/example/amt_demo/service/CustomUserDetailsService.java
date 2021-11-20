/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CustomUserDetailsService.java
 *
 * @brief
 */

package com.example.amt_demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {

    /**
     *
     * @param userId
     * @return
     */
    public UserDetails getUserById(int userId);
}