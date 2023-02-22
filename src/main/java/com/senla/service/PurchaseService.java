package com.senla.service;

import com.senla.service.dto.PurchaseDto;

import java.util.List;

public interface PurchaseService {
    void save(PurchaseDto purchaseDto);
    PurchaseDto findById(Long id);
    List<PurchaseDto> findAll();
    void delete(PurchaseDto purchaseDto);
    void update(PurchaseDto purchaseDto);


}
