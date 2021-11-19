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

    /**
     * Default constructor of Carpet
     */
    public Carpet() {

    }

    /**
     * Constructor of Carpet
     *
     * @param name        - the name of the Carpet
     * @param description - the description of the Carpet
     * @param price       - the price of the Carpet
     */
    public Carpet(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = 0;
    }

    /**
     * Constructor of Carpet
     *
     * @param name        - the name of the Carpet
     * @param description - the description of the Carpet
     * @param price       - the price of the Carpet
     * @param quantity    - the quantity of the Carpet
     */
    public Carpet(String name, String description, Double price, Integer quantity) {
        this(name, description, price);
        this.quantity = quantity;
    }

    public Carpet(Integer id, String name, String description, Double price, Integer quantity) {
        this(name, description, price, quantity);
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public void setPhotos(List<CarpetPhoto> photos) {
        this.photos = photos;
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
    public String getFirstPhotoPath() {

        String path = "carpet-photos/placeholder-image.png";

        if (!photos.isEmpty()) {
            path = photos.get(0).getPath();
        }
        return path;
    }

}

