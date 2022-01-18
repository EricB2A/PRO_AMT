/**
 * @team AMT - Silkyroad
 * @authors Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file ArticlePhoto.java
 *
 * @brief Model representing photos associated to an article
 */

package com.example.amt_demo.model;

import javax.persistence.*;

@Entity
public class ArticlePhoto {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String path;

    /**
     * Default constructor of ArticlePhoto
     */
    public ArticlePhoto() {

    }

    /**
     * Constructor of ArticlePhoto
     * @param path  the path to the photo of the ArticlePhoto
     */
    public ArticlePhoto(Integer id, String path) {
        this.id = id;
        this.path = path;
    }

    /**
     * Constructor of ArticlePhoto
     * @param path  the path to the photo of the ArticlePhoto
     */
    public ArticlePhoto(String path) {
        this.path = path;
    }

    /**
     * Getter of the id of the ArticlePhoto
     * @return  the id of the ArticlePhoto
     */
    public Integer getId() {
        return id;
    }

    /**
     * Setter of the id of the ArticlePhoto
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getPath() {
        return path;
    }

}