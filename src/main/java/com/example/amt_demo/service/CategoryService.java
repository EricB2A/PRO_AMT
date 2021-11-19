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

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    public void addCategoryToCarpet(Integer valueOf, Integer valueOf1) {
        categoryRepository.addCategoryToCarpet(valueOf, valueOf1);
    }

    public List<Category> getCategoriesOfCarpet(Integer valueOf) {
        return categoryRepository.getCategoriesOfCarpet(valueOf);
    }

    public Category findId(Integer valueOf) {
        return categoryRepository.findId(valueOf);
    }

    public Set<Category> hasArticlesInCategory(Integer valueOf) {
        return categoryRepository.hasArticlesInCategory(valueOf);
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }
}