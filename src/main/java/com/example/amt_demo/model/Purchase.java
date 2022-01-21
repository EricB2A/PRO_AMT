package com.example.amt_demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
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

    @OneToMany
    @JoinColumn(name = "purchase_id")
    private List<ArticlePurchased> article;

    public Purchase(Long userId) {
        id = userId;
    }

    public void addArticle(ArticlePurchased article) {
        this.article.add(article);
    }

}
