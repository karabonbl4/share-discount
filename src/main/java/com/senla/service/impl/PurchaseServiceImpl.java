package com.senla.service.impl;

import com.senla.dao.PurchaseRepository;
import com.senla.exceptions.EntityNotFoundException;
import com.senla.model.entity.Purchase;
import com.senla.service.PurchaseService;
import com.senla.model.dto.PurchaseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    private final ModelMapper modelMapper;

    private final PurchaseRepository purchaseRepository;


    @Override
    public PurchaseDto save(PurchaseDto purchaseDto) {
        Purchase purchase = modelMapper.map(purchaseDto, Purchase.class);
        Purchase returnedPurchaseAfterSaving = purchaseRepository.save(purchase);
        return modelMapper.map(returnedPurchaseAfterSaving, PurchaseDto.class);
    }

    @Override
    public PurchaseDto findById(Long id) throws EntityNotFoundException {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(purchase, PurchaseDto.class);
    }

    @Override
    public List<PurchaseDto> findAll() {
        return purchaseRepository.findAll().stream()
                .map(purchase -> modelMapper.map(purchase, PurchaseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long purchaseId) {
        Purchase purchaseForDeleting = purchaseRepository.getReferenceById(purchaseId);
        purchaseRepository.delete(purchaseForDeleting);
    }

    @Override
    public PurchaseDto update(PurchaseDto purchaseDto) {
        Purchase purchase = modelMapper.map(purchaseDto, Purchase.class);
        Purchase returnedPurchaseAfterUpdating = purchaseRepository.saveAndFlush(purchase);
        return modelMapper.map(returnedPurchaseAfterUpdating, PurchaseDto.class);
    }

    @Override
    public List<PurchaseDto> findByCardId(Long id) {
        return purchaseRepository.findByCard_Id(id).stream()
                .map(purchase -> modelMapper.map(purchase, PurchaseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseDto> findByUserId(Long id) {
        return purchaseRepository.findByUser_Id(id).stream()
                .map(purchase -> modelMapper.map(purchase, PurchaseDto.class))
                .collect(Collectors.toList());
    }
}
