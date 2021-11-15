package com.example.amt_demo.model;

import javax.persistence.*;

@Entity
public class CarpetPhoto {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String path;

    public CarpetPhoto() {

    }

    public CarpetPhoto(String path) {
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
