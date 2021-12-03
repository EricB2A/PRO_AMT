/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CategoryService.java
 *
 * @brief Service of Category
 */

package com.example.amt_demo.service;

import com.example.amt_demo.model.Category;
import com.example.amt_demo.model.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryService {

    final private CategoryRepository categoryRepository;

    /**
     * Constructor of CategoryService
     * @param categoryRepository the CategoryRepository
     */
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Method finding every Category
     * @return the list of Category objects
     */
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    /**
     * Method finding the Category by its id
     * @param id the id of the Category
     * @return the Category
     */
    public Category findById(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()) {
            return category.get();
        }
        return null;
    }

    /**
     * Method finding the Category by its name
     * @param name the name of the Category
     * @return the Category
     */
    public Category findByName(String name) {
        Optional<Category> category = categoryRepository.findByName(name);
        if(category.isPresent()) {
            return category.get();
        }
        return null;
    }

    /**
     * Method saving the Category to the database
     * @param category the Category
     */
    public void save(Category category) {
        categoryRepository.save(category);
    }

    /**
     * Method getting every Category
     * @return the list of Category
     */
    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    /**
     * Method getting every Category linked to an Article
     * @param articleId the id of the Article
     * @return the list of Category
     */
    public List<Category> getCategoriesOfCarpet(Long articleId) {
        return categoryRepository.getCategoriesOfArticle(articleId);
    }

    /**
     * Method checking if a Category has any Article object
     * @param categoryId the id of the Category
     * @return the list of Category
     */
    public Set<Category> hasArticlesInCategory(Integer categoryId) {
        return categoryRepository.hasArticlesInCategory(categoryId);
    }

    /**
     * Method deleting a Category
     * @param category the Category
     */
    public void delete(Category category) {
        categoryRepository.delete(category);
    }
}