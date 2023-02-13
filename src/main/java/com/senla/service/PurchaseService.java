package com.senla.service;

import com.senla.model.dto.PurchaseDto;

import java.util.List;

public interface PurchaseService {
    void save(PurchaseDto purchaseDto);
    PurchaseDto findById(Long id);
    List<PurchaseDto> findAll();
    void delete(Long purchaseId);
    void update(PurchaseDto purchaseDto);
    List<PurchaseDto> findByCardId(Long id);
    List<PurchaseDto> findByUserId(Long id);


}
