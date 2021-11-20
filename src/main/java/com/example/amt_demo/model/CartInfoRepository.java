/**
 * @team AMT - Silkyroad
 * @authors Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file CartInfoRepository.java
 *
 * @brief
 */

package com.example.amt_demo.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CartInfoRepository extends CrudRepository<CartInfo, Integer> {

    /**
     *
     * @param userId
     * @return
     */
    @Query("select ci from CartInfo ci where ci.user.id = :userId")
    List<CartInfo> findCartInfosByUserId(int userId);

    /**
     *
     * @param carpetId
     * @param userId
     * @return
     */
    @Query("select ci from CartInfo ci where ci.article.id = ?1 and ci.user.id = ?2")
    List<CartInfo> findCartInfosByCarpetAndByUser(int carpetId, int userId);

    /**
     *
     * @param carpetId
     * @param userId
     */
    @Transactional
    @Modifying
    @Query("delete from CartInfo ci where ci.article.id = :carpetId and ci.user.id = :userId")
    void deleteCartInfoByCarpetIdAndByUserId(int carpetId, int userId);

    /**
     *
     * @param carpetId
     * @param userId
     * @param quantity
     */
    @Transactional
    @Modifying
    @Query("update CartInfo ci set ci.quantity = :quantity where ci.article.id = :carpetId and ci.user.id = :userId")
    void setCartInfoQuantityByCarpetIdAndByUserId(int carpetId, int userId, int quantity);
}