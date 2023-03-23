package com.senla.dao;

import com.senla.model.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(value = "fetch.roles", type = EntityGraph.EntityGraphType.FETCH)
    User findByUsername(String username);

    User findByCoupons_Id(Long id);

    User findByCards_Id(Long id);

    List<User> findByTrademarks_Id(Long trademarkId);

    @Modifying
    @Query(value = "INSERT INTO admin_trademark at (at.user_id, at.trademark_id) VALUES (:adminId, :trademarkId)", nativeQuery = true)
    void setAdminTrademark(@Param("adminId") Long adminId, @Param("trademarkId") Long trademarkId);

    @Modifying
    @Query(value = "DELETE FROM admin_trademark WHERE user_id = :adminId AND trademark_id=:trademarkId", nativeQuery = true)
    void removeAdminTrademark(@Param("adminId") Long adminId, @Param("trademarkId") Long trademarkId);

    @Modifying
    @Query(value = "UPDATE User SET score=:newScore WHERE id = :userId")
    void setNewScoreForUser(@Param(value = "newScore") BigDecimal newScore, @Param(value = "userId") Long userId);
}
