package com.example.amt_demo.model;

/**
 * @author Eric (@EricB2A)
 */
public class CartInfo {

    private Carpet article;
    private int quantity;

    public CartInfo(Carpet article, int quantity){
        this.article = article;
        this.quantity = quantity;
    }

    public Carpet getArticle() {
        return article;
    }

    public void setArticle(Carpet article) {
        this.article = article;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
