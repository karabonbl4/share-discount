package com.senla.service;

import com.senla.model.dto.DiscountPolicyDto;
import com.senla.model.dto.save.DiscountPolicyForSave;

import java.util.List;

public interface DiscountPolicyService {
    DiscountPolicyDto save(DiscountPolicyForSave discountPolicyDto);

    DiscountPolicyDto findById(Long id);

    List<DiscountPolicyDto> findAll(Integer pageNumber, Integer pageSize);

    void delete(Long discountPolicyId);

    DiscountPolicyDto update(DiscountPolicyDto discountPolicyDto);

    List<DiscountPolicyDto> findByTrademarkId(Long id, Integer pageNumber, Integer pageSize);

    DiscountPolicyDto findByCardId(Long cardId);
}
