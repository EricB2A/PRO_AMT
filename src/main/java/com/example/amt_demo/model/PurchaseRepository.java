package com.example.amt_demo.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PurchaseRepository extends CrudRepository<Category, Integer> {
    @Query("SELECT distinct p FROM Purchase p JOIN p.article pa WHERE p.userId = ?1")
    List<Purchase> findByUser(int id);
}
