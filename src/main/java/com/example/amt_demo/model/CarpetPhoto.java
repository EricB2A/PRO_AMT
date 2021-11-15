package com.example.amt_demo.model;

import javax.persistence.*;

@Entity
public class CarpetPhoto {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String path;

    public CarpetPhoto() {

    }

    public CarpetPhoto(String path) {
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
