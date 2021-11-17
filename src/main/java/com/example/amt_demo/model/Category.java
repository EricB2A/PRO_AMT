package com.example.amt_demo.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    private Boolean checked;

    @ManyToMany(mappedBy = "categories")
    private Set<Carpet> carpets = new HashSet<>();


    public Category() {

    }

    public Category(String name) {
        this.name = name;
    }

    public Category(int id, String name, Boolean checked) {
        this.id = id;
        this.name = name;
        this.checked = checked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
