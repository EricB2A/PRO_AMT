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
     *
     * @param categoryRepository
     */
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     *
     * @return
     */
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }

    /**
     *
     * @param name
     * @return
     */
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    /**
     *
     * @param category
     */
    public void save(Category category) {
        categoryRepository.save(category);
    }

    /**
     *
     * @return
     */
    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    /**
     *
     * @param valueOf
     * @param valueOf1
     */
    public void addCategoryToCarpet(Integer valueOf, Integer valueOf1) {
        categoryRepository.addCategoryToCarpet(valueOf, valueOf1);
    }

    /**
     *
     * @param valueOf
     * @return
     */
    public List<Category> getCategoriesOfCarpet(Integer valueOf) {
        return categoryRepository.getCategoriesOfCarpet(valueOf);
    }

    /**
     *
     * @param valueOf
     * @return
     */
    public Category findId(Integer valueOf) {
        return categoryRepository.findId(valueOf);
    }

    /**
     *
     * @param valueOf
     * @return
     */
    public Set<Category> hasArticlesInCategory(Integer valueOf) {
        return categoryRepository.hasArticlesInCategory(valueOf);
    }

    /**
     *
     * @param category
     */
    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    /**
     *
     * @param article_id
     */
    public void deleteExistingCategoryToCarpet(Integer article_id) {
        categoryRepository.deleteExistingCategoryToCarpet(article_id);
    }
}