package com.senla.dao;

import com.senla.model.entity.DiscountCard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountCardRepository extends JpaRepository<DiscountCard, Long> {

    List<DiscountCard> findByOwner_Id(Long userId, Pageable paging);

    @EntityGraph(value = "fetch.discount-card.policy", type = EntityGraph.EntityGraphType.FETCH)
    DiscountCard findByNumberAndDiscountPolicy_Trademark_Id(Long cardNumber, Long trademarkId);

    @Query(value = "SELECT dc FROM DiscountCard dc WHERE discount BETWEEN :discountMin AND :discountMax GROUP BY id HAVING isRent = false")
    List<DiscountCard> findCardsWithSortForRent(@Param("discountMin") BigDecimal discountMin, @Param("discountMax") BigDecimal discountMax, Pageable paging);

    @Query(value = "SELECT AVG(dc.discount) FROM DiscountCard dc WHERE dc.owner.id=:userId")
    Optional<BigDecimal> getAverageDiscountFromAllHimCardsByUser(@Param("userId") Long userId);

    @Modifying
    @Query(value = "UPDATE DiscountCard dc SET dc.isRent=true WHERE dc.id=:cardId")
    void activateRentOfCard(@Param(value = "cardId") Long cardId);

    @Modifying
    @Query(value = "UPDATE DiscountCard dc SET dc.isRent=false WHERE dc.id=:cardId")
    void deactivateRentOfCard(@Param(value = "cardId") Long cardId);

    @Query(value = "SELECT dc FROM DiscountCard dc")
    List<DiscountCard> findAllWithSortForAdmin(Pageable paging);

    List<DiscountCard> findByDiscountPolicy_Trademark_Id(Long trademarkId, Pageable paging);

    @Modifying
    @Query(value = "UPDATE DiscountCard SET discount=:newDiscount WHERE id = :cardId")
    void setNewDiscountForCard(@Param(value = "newDiscount")BigDecimal newDiscount, @Param(value = "cardId") Long cardId);

    @EntityGraph(value = "fetch.discount-card.policy", type = EntityGraph.EntityGraphType.FETCH)
    DiscountCard getById(Long id);
}
