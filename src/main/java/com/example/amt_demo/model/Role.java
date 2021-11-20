/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file Role.java
 *
 * @brief Model representing the role
 */

package com.example.amt_demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Role {

    @Id
    private String name;

    /**
     * Getter of the name of the Role
     * @return the name of the Role
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name of the Role
     * @param name the name of the Role
     */
    public void setName(String name) {
        this.name = name;
    }
}
