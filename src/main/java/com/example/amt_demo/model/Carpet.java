package com.example.amt_demo.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


@Entity
public class Carpet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private Double price = 0.0;
    private Integer quantity = 0;

    @OneToMany(mappedBy = "carpet")
    Set<CartInfo> cartInfos;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> categories = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarpetPhoto> photos = new LinkedList<>();

    public Carpet() {
    }

    public Carpet(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = 0;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<CarpetPhoto> getPhotos() {
        return photos;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    // Used in View for article_thumbnail to get first photo
    public String getFirstPhotoPath(){

        String path = "carpet-photos/placeholder-image.png";

        if(!photos.isEmpty()){
            path = photos.get(0).getPath();
        }
        return path;
    }

}
