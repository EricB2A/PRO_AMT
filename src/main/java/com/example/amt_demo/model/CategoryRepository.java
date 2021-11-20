/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CategoryRepository.java
 *
 * @brief
 */

package com.example.amt_demo.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    /**
     * Method searching in database a Category by its name
     * @param name the name
     * @return the Category with the wanted name
     */
    @Query("SELECT c FROM Category c where c.name = ?1")
    Category findByName(String name);

    /**
     *
     * @param id
     * @return
     */
    @Query("SELECT c FROM Category c WHERE c.id = ?1")
    Category findId(int id);

    /**
     *
     * @param id
     * @return
     */
      @Query("SELECT c FROM Category c JOIN c.articles where c.id = ?1")
    Set<Category> hasArticlesInCategory(int id);

    /**
     *
     * @return
     */
    @Query("SELECT c FROM Category c WHERE c.id IN (SELECT c.id from Category c JOIN c.articles a)")
    List<Category> notEmptyCategory();

    /**
     *
     * @param article_id
     * @return
     */
    @Query("SELECT c from Category c JOIN c.articles a WHERE a.id = ?1")
    List<Category> getCategoriesOfCarpet(Integer article_id);

    /**
     *
     * @return
     */
    @Query("SELECT c from Category c")
    List<Category> getAllCategories();


}