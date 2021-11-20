/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file RoleRepository.java
 *
 * @brief TODO
 */

package com.example.amt_demo.model;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, String> {
    Role findByName(String name);
}