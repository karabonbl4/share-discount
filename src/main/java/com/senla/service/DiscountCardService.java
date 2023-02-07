package com.senla.service;

import com.senla.service.dto.DiscountCardDto;

import java.util.List;

public interface DiscountCardService {
    void save(DiscountCardDto discountCardDto);
    DiscountCardDto findById(Long id);
    List<DiscountCardDto> findAll();
    void delete(DiscountCardDto discountCardDto);
    void update(DiscountCardDto discountCardDto);
}
