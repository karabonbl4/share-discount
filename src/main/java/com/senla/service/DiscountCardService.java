package com.senla.service;

import com.senla.model.dto.DiscountCardDto;
import com.senla.model.dto.rent.DiscountCardForRentDto;
import com.senla.model.dto.save.DiscountCardWithPolicy;
import com.senla.model.dto.save.DiscountCardWithTrademarkDto;
import com.senla.model.entity.DiscountCard;

import java.math.BigDecimal;
import java.util.List;

public interface DiscountCardService {
    DiscountCardDto save(DiscountCardWithTrademarkDto discountCardDto);

    DiscountCardDto getById(Long id);

    List<DiscountCardDto> findAllWithSort(Integer pageNumber, Integer pageSize, String[] sorting);

    void delete(Long id);

    DiscountCardDto update(DiscountCardWithTrademarkDto discountCardDto);

    List<DiscountCardDto> getCardsByUserId(Long userId, Integer pageNumber, Integer pageSize, String[] sortingBy);

    DiscountCardDto confirmDiscountCardToSave(DiscountCardWithTrademarkDto discountCardDto);

    DiscountCardForRentDto confirmRentCard(DiscountCardForRentDto rentCard);

    List<DiscountCardDto> getMyCards(Integer pageNumber, Integer pageSize, String[] sortingBy);

    List<DiscountCardDto> getCardsForAdminTrademark(Long trademarkId, Integer pageNumber, Integer pageSize, String[] sortingBy);

    String sendRequestForRentCard(Long cardId, String startRent, String endRent);

    <T> T getCardFromLink(String link, Class<T> clazz);

    String sendRequestForSaveCard(DiscountCardWithTrademarkDto discountCardWithTrademarkDto);

    DiscountCardWithPolicy getCardDtoWithPolicy(Long id);

    DiscountCard recalculateDiscountAfterPurchase(Long id, BigDecimal sumPurchase, BigDecimal sumAllPurchases);
}
