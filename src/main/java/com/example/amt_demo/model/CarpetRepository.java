package com.example.amt_demo.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CarpetRepository extends CrudRepository<Carpet, Integer> {
    @Query("select c from Carpet c where c.name = ?1")
    Carpet findByName(String name);

    @Query("select c from Carpet c where c.id = ?1")
    Carpet findId(int id);

    @Query("select c from Carpet c join c.categories cat where cat.name = ?1")
    Set<Carpet> findByFilter(String name);

    @Query (value = "SELECT * FROM carpet WHERE id IN (SELECT carpet.id FROM carpet INNER JOIN carpet_categories ON carpet.id = carpet_categories.carpets_id WHERE carpet_categories.categories_id = ?1)", nativeQuery = true)
    Iterable<Carpet> findErrorDeletion(int category_id);

    Optional<Carpet> findCarpetById(Integer id);
}
