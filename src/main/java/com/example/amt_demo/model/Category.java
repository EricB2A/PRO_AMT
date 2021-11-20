/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file Category.java
 *
 * @brief Model representing a category
 */

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
    private final Set<Article> articles = new HashSet<>();

    private Boolean checked;

    /**
     * Default constructor of Category
     */
    public Category() {

    }

    /**
     *
     * @param name
     */
    public Category(String name) {
        this.name = name;
    }

    /**
     *
     * @param id
     * @param name
     */
    public Category (Integer id, String name) {
        this(name);
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public Boolean getChecked() {
        return checked;
    }

    /**
     *
     * @param checked
     */
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    /**
     *
     * @return
     */
    public Set<Article> getCarpets() {
        return articles;
    }

    /**
     *
     * @param article
     */
    public void addCarpet(Article article) {
        this.articles.add(article);
    }
}