package com.senla.service.impl;

import com.senla.model.Purchase;
import com.senla.repository.PurchaseRepository;
import com.senla.service.PurchaseService;
import com.senla.service.dto.PurchaseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final ModelMapper modelMapper;
    private final PurchaseRepository purchaseRepository;


    @Override
    public void save(PurchaseDto purchaseDto) {
        Purchase purchase = modelMapper.map(purchaseDto, Purchase.class);
        purchaseRepository.save(purchase);
    }

    @Override
    public PurchaseDto findById(Long id) {
        Purchase purchase = purchaseRepository.findById(id);
        return modelMapper.map(purchase, PurchaseDto.class);
    }

    @Override
    public List<PurchaseDto> findAll() {
        return purchaseRepository.findAll().stream()
                .map(purchase -> modelMapper.map(purchase, PurchaseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(PurchaseDto purchaseDto) {
        Purchase purchase = modelMapper.map(purchaseDto, Purchase.class);
        purchaseRepository.delete(purchase);
    }

    @Override
    public void update(PurchaseDto purchaseDto) {
        Purchase purchase = modelMapper.map(purchaseDto, Purchase.class);
        purchaseRepository.update(purchase);
    }
}
