package com.senla.service;

import com.senla.service.dto.PurchaseDto;

import java.util.List;

public interface PurchaseService {
    PurchaseDto save(PurchaseDto purchaseDto);
    PurchaseDto findById(Long id);
    List<PurchaseDto> findAll();
    boolean delete(PurchaseDto purchaseDto);
    PurchaseDto update(PurchaseDto purchaseDto);
}
