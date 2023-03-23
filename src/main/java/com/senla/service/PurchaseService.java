package com.senla.service;

import com.senla.model.dto.PurchaseDto;

import java.util.List;

public interface PurchaseService {

    void save(PurchaseDto purchaseDto);

    PurchaseDto findById(Long id);

    List<PurchaseDto> findAll(Integer pageNumber, Integer pageSize);

    List<PurchaseDto> findByCardId(Long id, Integer pageNumber, Integer pageSize);

    List<PurchaseDto> findByUserId(Long id, Integer pageNumber, Integer pageSize);

    List<PurchaseDto> findByUserId(Long userId);

    List<PurchaseDto> findByTrademarkId(Long id, Integer pageNumber, Integer pageSize);
}
