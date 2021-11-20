/**
 * @team AMT - Silkyroad
 * @author Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file ArticleService.java
 *
 * @brief Service of Article
 */

package com.example.amt_demo.service;

import com.example.amt_demo.model.Article;
import com.example.amt_demo.model.ArticleRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleService {

    final private ArticleRepository articleRepository;

    /**
     * Constructor of CarpetService
     * @param articleRepository
     */
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     *
     * @return
     */
    public Iterable<Article> findAll() {
        return articleRepository.findAll();
    }

    /**
     *
     * @param name
     * @return
     */
    public Article findByName(String name) {
        return articleRepository.findByName(name);
    }

    /**
     *
     * @param newArticle
     */
    public void save(Article newArticle) {
        articleRepository.save(newArticle);
    }

    /**
     *
     * @param id
     * @return
     */
    public Optional<Article> findById(int id) {
        return articleRepository.findById(id);
    }

    /**
     *
     * @param article
     */
    public void delete(Article article) {
        articleRepository.delete(article);
    }

    /**
     *
     * @param id
     * @return
     */
    public Iterable<Article> findErrorDeletion(String id) {
        return articleRepository.findErrorDeletion(Integer.valueOf(id));
    }

    /**
     * TODO Safe to delete ?
     * @return
     */
    public long count() {
        return articleRepository.count();
    }
}