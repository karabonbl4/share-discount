package com.senla.service;

import com.senla.service.dto.DiscountPolicyDto;

import java.util.List;

public interface DiscountPolicyService {
    void save(DiscountPolicyDto discountPolicyDto);
    DiscountPolicyDto findById(Long id);
    List<DiscountPolicyDto> findAll();
    boolean delete(DiscountPolicyDto discountPolicyDto);
    void update(DiscountPolicyDto discountPolicyDto);
}
