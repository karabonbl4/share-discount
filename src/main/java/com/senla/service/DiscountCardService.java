package com.senla.service;

import com.senla.model.dto.DiscountCardDto;

import java.util.List;

public interface DiscountCardService {
    void save(DiscountCardDto discountCardDto);
    DiscountCardDto findById(Long id);
    List<DiscountCardDto> findAll();
    void delete(Long id);
    void update(DiscountCardDto discountCardDto);
    List<DiscountCardDto> getCardsByUserId(Long userId);
}
