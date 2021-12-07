/**
 * @team AMT - Silkyroad
 * @authors Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file UsernameJwtAuthenticationToken.java
 *
 * @brief TODO
 */

package com.example.amt_demo.auth;

import com.example.amt_demo.service.CustomUserDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.Assert;

import java.util.Collection;

public class UsernameJwtAuthenticationToken extends AbstractAuthenticationToken {

    private final CustomUserDetails principal;
    private Object credentials;
    private String jwtToken;

    /**
     * Constructor of UsernameJwtAuthenticationToken
     * @param principal
     * @param credentials
     */
    public UsernameJwtAuthenticationToken(CustomUserDetails principal, Object credentials) {
        super((Collection) null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    /**
     * Constructor of UsernameJwtAuthenticationToken
     * @param principal
     * @param credentials
     * @param authorities
     */
    public UsernameJwtAuthenticationToken(CustomUserDetails principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    /**
     * Constructor of UsernameJwtAuthenticationToken
     * @param principal
     * @param credentials
     * @param jwtToken
     * @param authorities
     */
    public UsernameJwtAuthenticationToken(CustomUserDetails principal, Object credentials, String jwtToken, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.jwtToken = jwtToken;
        super.setAuthenticated(true);
    }

    /**
     *
     * @return
     */
    public String getToken(){
        return this.jwtToken;
    }

    /**
     *
     * @return
     */
    public Object getCredentials() {
        return this.credentials;
    }

    /**
     *
     * @return
     */
    public Object getPrincipal() {
        return this.principal;
    }

    /**
     *
     * @param isAuthenticated
     * @throws IllegalArgumentException
     */
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    /**
     *
     */
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}