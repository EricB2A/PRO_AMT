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
     * Constructor of Category
     * @param name the name of the Category
     */
    public Category(String name) {
        this.name = name;
    }

    /**
     * Constructor of Category
     * @param id the id of the Category
     * @param name the name of the Category
     */
    public Category(Integer id, String name) {
        this(name);
        this.id = id;
    }

    /**
     * Getter of the id of the Category
     * @return the id of the Category
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter of the id of the Category
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter of the name of the Category
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name of the Category
     * @param name the name of the Category
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of the checked of the Category
     * @return the checked of the Category
     */
    public Boolean getChecked() {
        return checked;
    }

    /**
     * Setter of the checked of the Category
     * @param checked the checked of the Category
     */
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    /**
     * Getter of the Set of Articles of the Category
     * @return the Set of Articles
     */
    public Set<Article> getCarpets() {
        return articles;
    }

    /**
     * Method adding an Article to the list
     * @param article the Article
     */
    public void addCarpet(Article article) {
        this.articles.add(article);
    }
}