/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file UserRepository.java
 *
 * @brief
 */

package com.example.amt_demo.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}