package com.senla.service;

import com.senla.model.dto.DiscountPolicyDto;

import java.util.List;

public interface DiscountPolicyService {
    void save(DiscountPolicyDto discountPolicyDto);
    DiscountPolicyDto findById(Long id);
    List<DiscountPolicyDto> findAll();
    void delete(Long discountPolicyId);
    void update(DiscountPolicyDto discountPolicyDto);
    List<DiscountPolicyDto> findByTrademarkId(Long id);
}
