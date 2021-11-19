package com.example.amt_demo.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CartInfoRepository extends CrudRepository<CartInfo, Integer> {

    @Query("select ci from CartInfo ci where ci.user.id = :userId")
    List<CartInfo> findCartInfosByUserId(int userId);

    @Query("select ci from CartInfo ci where ci.carpet.id = ?1 and ci.user.id = ?2")
    List<CartInfo> findCartInfosByCarpetAndByUser(int carpetId, int userId);

    @Transactional
    @Modifying
    @Query("delete from CartInfo ci where ci.carpet.id = :carpetId and ci.user.id = :userId")
    void deleteCartInfoByCarpetIdAndByUserId(int carpetId, int userId);

    @Transactional
    @Modifying
    @Query("update CartInfo ci set ci.quantity = :quantity where ci.carpet.id = :carpetId and ci.user.id = :userId")
    void setCartInfoQuantityByCarpetIdAndByUserId(int carpetId, int userId, int quantity);
    
}
