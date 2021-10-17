package com.example.amt_demo.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;


@Entity
public class Carpet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private String imagePath;

    public Carpet() {
    }

    public Carpet(String name, String description, Double price) {
       this(name, description, price, "");
    }

    public Carpet(String name, String description, Double price, String imagePath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Transient
    public String getPhotosImagePath() {
        if (imagePath == null || id == null){
            return null;
        }

        return "/carpet-photos/" + id + "/" + imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imageName) {
        this.imagePath = imageName;
    }

}
