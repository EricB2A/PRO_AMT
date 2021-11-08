package com.example.amt_demo.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    @Query("select c from Category c where c.name = ?1")
    Category findByName(String name);

    @Query("select c from Category c where c.id = ?1")
    Category findId(int id);


    @Query(value = "select * from category c inner join carpet_categories cc on c.id = cc.categories_id where c.id = ?1", nativeQuery = true)
    List<Category> hasArticlesInCategory(int id);

    @Query(value = "select * from category c WHERE c.id IN (select c.id from category c inner join carpet_categories cc on c.id = cc.categories_id)", nativeQuery = true)
    List<Category> notEmptyCategory();
}
