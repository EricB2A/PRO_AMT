/**
 * @team AMT - Silkyroad
 * @authors Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file ArticleRepository.java
 *
 * @brief TODO
 */

package com.example.amt_demo.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface ArticleRepository extends CrudRepository<Article, Integer> {

    /**
     *
     * @param name
     * @return
     */
    @Query("SELECT a FROM Article a WHERE a.name = ?1")
    Article findByName(String name);
    
    /**
     *
     * @param name
     * @return
     */
    @Query("SELECT a FROM Article a JOIN a.categories cat WHERE cat.name = ?1")
    Set<Article> findByFilter(String name);

    /**
     *
     * @param category_id
     * @return
     */
    @Query (value = "SELECT a FROM Article a WHERE a.id IN (SELECT a.id FROM Article a JOIN a.categories cat WHERE cat.id = ?1)")
    Iterable<Article> findErrorDeletion(int category_id);

}