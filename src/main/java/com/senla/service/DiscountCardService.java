package com.senla.service;

import com.senla.service.dto.DiscountCardDto;

import java.util.List;

public interface DiscountCardService {
    DiscountCardDto save(DiscountCardDto discountCardDto);
    DiscountCardDto findById(Long id);
    List<DiscountCardDto> findAll();
    boolean delete(DiscountCardDto discountCardDto);
    DiscountCardDto update(DiscountCardDto discountCardDto);
}
