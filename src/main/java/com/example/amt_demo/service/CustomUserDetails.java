/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CustomUserDetails.java
 *
 * @brief
 */

package com.example.amt_demo.service;

import com.example.amt_demo.model.User;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    public static final String ROLE_PREFIX = "ROLE_";
    private final User user;

    /**
     *
     * @param user
     */
    public CustomUserDetails(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> rolesList =  new ArrayList<>();
        rolesList.add(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole())) ;
        return rolesList;
    }

    /**
     *
     * @return
     */
    @Override
    public String getPassword() {
        new ExecutionControl.NotImplementedException("Password aren't stored in the backend");
        return "";
    }

    /**
     *
     * @return
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}