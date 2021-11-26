/**
 * @team AMT - Silkyroad
 * @authors Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file Article.java
 *
 * @brief Model representing an article of the e-shop
 */

package com.example.amt_demo.model;

import reactor.util.annotation.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private Double price = 0.0;
    private Integer quantity = 0;

    @OneToMany(mappedBy = "article")
    Set<CartInfo> cartInfos;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> categories = new HashSet<>();

    // Each Article can have 0+ photo
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticlePhoto> photos = new LinkedList<>();

    /**
     * Default constructor of Article
     */
    public Article() {

    }

    /**
     * Constructor of Article
     * @param name          the name of the Article
     * @param description   the description of the Article
     * @param price         the price of the Article
     */
    public Article(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = 0;
    }

    /**
     * Constructor of Article
     * @param name          the name of the Article
     * @param description   the description of the Article
     * @param price         the price of the Article
     * @param quantity      the quantity of the Article
     */
    public Article(String name, String description, Double price, Integer quantity) {
        this(name, description, price);
        this.quantity = quantity;
    }

    /**
     * Constructor of Article
     * @param id            the id of the Article
     * @param name          the name of the Article
     * @param description   the description of the Article
     * @param price         the price of the Article
     * @param quantity      the quantity of the Article
     */
    public Article(Integer id, String name, String description, Double price, Integer quantity) {
        this(name, description, price, quantity);
        this.id = id;
    }

    /**
     * Getter of the id of the Article
     * @return  the id of the Article
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter of the id of the Article
     * @param id    the id of the Article
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter of the name of the Article
     * @return  the name of the article
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name of the Article
     * @param name  the name of the Article
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of the description of the Article
     * @return  the description of the Article
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter of the description of the Article
     * @param description   the description of the Article
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter of the price of the Article
     * @return the price of the Article
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Setter of the price of the Article
     * @param price the price of the Article
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Getter of the quantity of the Article
     * @return the quantity of the Article
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Setter of the quantity of the Article
     * @param quantity  the quantity of the Article
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Getter of the Set of Category of the Article
     * @return the Set of Category of the Article
     */
    public Set<Category> getCategories() {
        return categories;
    }

    /**
     * Setter of the Set of Category of the Article
     * @param categories    the Set of Category of the Article
     */
    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    /**
     * Getter of the List of ArticlePhoto of the Article
     * @return the List of ArticlePhoto of the Article
     */
    public List<ArticlePhoto> getPhotos() {
        return photos;
    }

    /**
     * Setter of the List of ArticlePhoto of the Article
     * @param photos    the List of ArticlePhoto of the Article
     */
    public void setPhotos(List<ArticlePhoto> photos) {
        this.photos = photos;
    }

    /**
     *
     * @return
     */
    public String getFirstPhotoPath() {
        // Used in View for article_thumbnail to get first photo
        String path = "images/placeholder-image.png";

        if (!photos.isEmpty()) {
            path = photos.get(0).getPath();
        }
        return path;
    }

    public Article get() {
        return this;
    }
}