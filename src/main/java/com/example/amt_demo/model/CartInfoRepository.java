/**
 * @team AMT - Silkyroad
 * @authors Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CartInfoRepository.java
 *
 * @brief TODO
 */

package com.example.amt_demo.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CartInfoRepository extends CrudRepository<Cart, Integer> {

    /**
     *
     * @param userId
     * @return
     */
    @Query("select ci from Cart ci where ci.user.id = :userId")
    List<Cart> findCartInfosByUserId(int userId);

    /**
     *
     * @param carpetId
     * @param userId
     * @return
     */
    @Query("select ci from Cart ci where ci.article.id = ?1 and ci.user.id = ?2")
    List<Cart> findCartInfosByCarpetAndByUser(int carpetId, int userId);

    /**
     *
     * @param carpetId
     * @param userId
     */
    @Transactional
    @Modifying
    @Query("delete from Cart ci where ci.article.id = :carpetId and ci.user.id = :userId")
    void deleteCartInfoByCarpetIdAndByUserId(int carpetId, int userId);

    /**
     *
     * @param carpetId
     * @param userId
     * @param quantity
     */
    @Transactional
    @Modifying
    @Query("update Cart ci set ci.quantity = :quantity where ci.article.id = :carpetId and ci.user.id = :userId")
    void setCartInfoQuantityByCarpetIdAndByUserId(int carpetId, int userId, int quantity);

    @Transactional
    @Modifying
    @Query("delete from Cart ci where ci.user.id = :userId")
    void deleteAllCartInfoByUser(int userId);
}