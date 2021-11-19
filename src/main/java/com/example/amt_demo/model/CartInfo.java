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
    @JoinColumn(name = "carpet_id")
    private Carpet carpet;
    
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public CartInfo(Carpet carpet, int quantity, User user){
        this.carpet = carpet;
        this.user = user;
        this.quantity = quantity;
    }

    public CartInfo(Carpet carpet, int quantity){
        this(carpet, quantity, null);
    }

    public CartInfo() {

    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(carpet.getId(), ((CartInfo) obj).carpet.getId());
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Carpet getCarpet() {
        return carpet;
    }

    public void setCarpet(Carpet carpet) {
        this.carpet = carpet;
    }
}
