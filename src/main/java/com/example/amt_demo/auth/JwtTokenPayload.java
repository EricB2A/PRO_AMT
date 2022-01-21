/**
 * @team AMT - Silkyroad
 * @authors Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file JwtTokenPayload.java
 *
 * @brief TODO
 */

package com.example.amt_demo.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtTokenPayload {
    public static final String USERNAME = "sub";
    public static final String ROLE = "role";
    public static final String ID = "id";

    private int id;
    private String username;
    private String role;
}