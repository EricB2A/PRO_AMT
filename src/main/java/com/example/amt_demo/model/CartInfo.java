/**
 * @team AMT - Silkyroad
 * @authors Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CartInfo.java
 *
 * @brief Model representing the cart/basket
 */

package com.example.amt_demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class CartInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
    
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Default constructor of CartInfo
     */
    public CartInfo() {

    }

    /**
     * Constructor of CartInfo (for an offline user)
     * @param article   the Article in the CartInfo
     * @param quantity  the quantity in the CartInfo
     */
    public CartInfo(Article article, int quantity){
        this(article, quantity, null);
    }

    /**
     * Constructor of CartInfo (for an online user)
     * @param article   the object Article in the cart
     * @param quantity  the quantity associated to the Article
     * @param user      the user willing to buy the Article
     */
    public CartInfo(Article article, int quantity, User user){
        this.article = article;
        this.user = user;
        this.quantity = quantity;
    }

    /**
     * Getter of the id of the CartInfo
     * @return the id of the CartInfo
     */
    public int getId() {
        return id;
    }

    /**
     * Setter of the id of the CartInfo
     * @param id    the id of the CartInfo
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Article getArticle() {
        return article;
    }

    /**
     *
     * @param article
     */
    public void setArticle(Article article) {
        this.article = article;
    }

    /**
     *
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return Objects.equals(article.getId(), ((CartInfo) obj).article.getId());
    }
}