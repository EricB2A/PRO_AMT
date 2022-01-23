package com.example.amt_demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer userId;

    private Date purchaseDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticlePurchased> article = new LinkedList<>();

    public Purchase(Long userId) {
        this.userId = Math.toIntExact(userId);
        this.purchaseDate = new Date();
    }

    public void addArticle(ArticlePurchased article) {
        this.article.add(article);
    }
}
