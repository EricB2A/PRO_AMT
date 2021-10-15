package com.example.amt_demo.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CarpetRepository extends CrudRepository<Carpet, Integer> {
    @Query("select c from Carpet c where c.name = ?1")
    Carpet findByName(String name);
}
