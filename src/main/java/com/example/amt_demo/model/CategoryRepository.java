/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CategoryRepository.java
 *
 * @brief Class about executing queries relating Category
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
     * Method finding category with associated Article object
     * @param id the id of the Category
     * @return the list of Category and Article associated
     */
      @Query("SELECT c FROM Category c JOIN c.articles where c.id = ?1")
    Set<Category> hasArticlesInCategory(int id);

    /**
     * Method finding every non-empty Category (with at least one Article in it)
     * Used for the filter
     * @return the list of Category
     */
    @Query("SELECT c FROM Category c WHERE c.id IN (SELECT c.id from Category c JOIN c.articles a)")
    List<Category> notEmptyCategory();

    /**
     * Method finding every Category associated with an Article
     * @param article_id the id of the Article
     * @return the list of Category associated with the Article
     */
    @Query("SELECT c from Category c JOIN c.articles a WHERE a.id = ?1")
    List<Category> getCategoriesOfArticle(Integer article_id);

    /**
     * Method finding every category in the database
     * @return the list of Category
     */
    @Query("SELECT c from Category c")
    List<Category> getAllCategories();
}