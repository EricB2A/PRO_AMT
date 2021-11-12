package com.example.amt_demo.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    @Query("select c from Category c where c.name = ?1")
    Category findByName(String name);

    @Query("select c from Category c where c.id = ?1")
    Category findId(int id);


    @Query(value = "select * from category c inner join carpet_categories cc on c.id = cc.categories_id where c.id = ?1", nativeQuery = true)
    List<Category> hasArticlesInCategory(int id);

    @Query(value = "select * from category c WHERE c.id IN (select c.id from category c inner join carpet_categories cc on c.id = cc.categories_id)", nativeQuery = true)
    List<Category> notEmptyCategory();

    @Query(value = "SELECT * from category where id NOT IN (SELECT category.id FROM carpet \n" +
            "INNER JOIN carpet_categories ON carpet.id = carpet_categories.carpets_id\n" +
            "INNER JOIN category on carpet_categories.categories_id = category.id\n" +
            "WHERE carpet.id = ?1)", nativeQuery = true)
    Set<Category> findCategoryNotBelongingToCarpet(int id);

    @Modifying
    @Transactional
    @Query(value = "Insert into carpet_categories(carpets_id, categories_id) VALUES (?1, ?2)", nativeQuery = true)
    void addCategoryToCarpet(int carpet_id, int category_id);
}
