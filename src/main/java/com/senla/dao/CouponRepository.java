package com.senla.dao;

import com.senla.model.entity.Coupon;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Coupon getCouponByPurchase_Id(Long purchaseId);

    List<Coupon> getByTrademark_Id(Long trademarkId);

    List<Coupon> getByUser_Id(Long userId, Pageable paging);

    List<Coupon> findDistinctByIsUsedFalseAndEndDateGreaterThanEqualAndUser_IdNull(LocalDate now, Pageable paging);

    @Query(value = "SELECT id, \"name\", start_date, end_date, discount, used, trademark_id, user_id, purchase_id FROM " +
            "(SELECT coupon.id, coupon.\"name\", coupon.start_date, coupon.end_date, coupon.discount, coupon.used, coupon.trademark_id, coupon.user_id, coupon.purchase_id FROM coupon  \n" +
            "LEFT OUTER JOIN trademark t ON coupon.trademark_id = t.id\n" +
            "LEFT OUTER JOIN admin_trademark at2 ON at2.trademark_id = t.id\n" +
            "LEFT OUTER JOIN \"user\" u_1 ON at2.user_id = u_1.id \n" +
            "where u_1.id=:adminId\n" +
            "UNION \n" +
            "SELECT coupon.id, coupon.\"name\", coupon.start_date, coupon.end_date, coupon.discount, coupon.used, coupon.trademark_id, coupon.user_id, coupon.purchase_id FROM coupon  \n" +
            "LEFT OUTER JOIN \"user\" u_2 ON coupon.user_id = u_2.id \n" +
            "WHERE coupon.used  = FALSE AND coupon.end_date >=:localNow AND (u_2.id IS NULL)) t ", nativeQuery = true)
    List<Coupon> findForAdminTrademark(@Param(value = "adminId") Long adminId,@Param(value = "localNow") LocalDate now, Pageable paging);

    @Modifying
    @Query(value = "UPDATE Coupon c SET c.isUsed=true WHERE c.id= :couponId")
    void deactivateCoupon(@Param(value = "couponId") Long couponId);
}
