package com.senla.service.impl;

import com.senla.model.Purchase;
import com.senla.repository.impl.PurchaseRepository;
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
    public PurchaseDto save(PurchaseDto purchaseDto) {
        Purchase purchase = modelMapper.map(purchaseDto, Purchase.class);
        Purchase purchase1 = purchaseRepository.saveOrUpdate(purchase);
        return modelMapper.map(purchase1, PurchaseDto.class);
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
    public boolean delete(PurchaseDto purchaseDto) {
        Purchase purchase = modelMapper.map(purchaseDto, Purchase.class);
        purchaseRepository.delete(purchase);
        return purchaseRepository.findById(purchaseDto.getId()) == null;
    }

    @Override
    public PurchaseDto update(PurchaseDto purchaseDto) {
        Purchase purchase = modelMapper.map(purchaseDto, Purchase.class);
        return modelMapper.map(purchaseRepository.saveOrUpdate(purchase), PurchaseDto.class);
    }
}
