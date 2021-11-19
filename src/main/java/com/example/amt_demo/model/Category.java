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

    @ManyToMany(mappedBy = "categories")
    private final Set<Carpet> carpets = new HashSet<>();

    private Boolean checked;


    public Category() {

    }

    public Category(String name) {
        this.name = name;
    }

    public Category (Integer id, String name) {
        this(name);
        this.id = id;
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

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Set<Carpet> getCarpets() {
        return carpets;
    }

    public void addCarpet(Carpet carpet) {
        this.carpets.add(carpet);
    }
}