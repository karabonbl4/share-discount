package com.senla.service.impl;

import com.senla.dao.PurchaseRepository;
import com.senla.exceptions.EntityNotFoundException;
import com.senla.exceptions.ObjectAlreadyExistException;
import com.senla.model.dto.UserDto;
import com.senla.model.entity.DiscountCard;
import com.senla.model.entity.Purchase;
import com.senla.model.entity.User;
import com.senla.service.DiscountCardService;
import com.senla.service.PurchaseService;
import com.senla.model.dto.PurchaseDto;
import com.senla.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    private final ModelMapper modelMapper;

    private final PurchaseRepository purchaseRepository;

    private final UserService userService;

    private final DiscountCardService cardService;

    @SneakyThrows
    @Override
    public void save(PurchaseDto purchaseDto) {
        if (purchaseDto.getName() != null && purchaseRepository.findByName(purchaseDto.getName()) != null) {
            throw new ObjectAlreadyExistException(purchaseDto.getName());
        }
        UserDto flashBuyer = userService.findById(purchaseDto.getBuyer().getId());
        UserDto ownersCard = userService.findByCardId(purchaseDto.getCard().getId());
        BigDecimal sumPurchases = purchaseRepository.findByCard_Id(purchaseDto.getCard().getId(), Pageable.unpaged()).stream().map(Purchase::getSum).reduce(BigDecimal.ZERO, BigDecimal::add);
        User savedBuyer = userService.recalculateScore(ownersCard, flashBuyer, this.calculateAddingScore(purchaseDto));
        DiscountCard savedCard = cardService.recalculateDiscountAfterPurchase(purchaseDto.getCard().getId(), purchaseDto.getSum(), sumPurchases);
        Purchase savingPurchase = modelMapper.map(purchaseDto, Purchase.class);
        savingPurchase.setBuyer(savedBuyer);
        savingPurchase.setCard(savedCard);
        purchaseRepository.saveAndFlush(savingPurchase);
    }

    @Override
    public PurchaseDto findById(Long id) {
        Purchase purchase = purchaseRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(purchase, PurchaseDto.class);
    }

    @Override
    public List<PurchaseDto> findAll(Integer pageNumber, Integer pageSize) {
        Pageable paging = this.getPaging(pageNumber, pageSize);
        return purchaseRepository.findAll(paging).stream()
                .map(purchase -> modelMapper.map(purchase, PurchaseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseDto> findByCardId(Long id, Integer pageNumber, Integer pageSize) {
        Pageable paging = this.getPaging(pageNumber, pageSize);
        return purchaseRepository.findByCard_Id(id, paging).stream()
                .map(purchase -> modelMapper.map(purchase, PurchaseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseDto> findByUserId(Long id, Integer pageNumber, Integer pageSize) {
        Pageable paging = this.getPaging(pageNumber, pageSize);
        return purchaseRepository.findByBuyer_Id(id, paging).stream()
                .map(purchase -> modelMapper.map(purchase, PurchaseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseDto> findByUserId(Long id) {
        return purchaseRepository.findByBuyer_Id(id, Pageable.unpaged()).stream()
                .map(purchase -> modelMapper.map(purchase, PurchaseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseDto> findByTrademarkId(Long id, Integer pageNumber, Integer pageSize) {
        Pageable paging = this.getPaging(pageNumber, pageSize);
        return purchaseRepository.findByCard_DiscountPolicy_Trademark_Id(id, paging).stream()
                .map(purchase -> modelMapper.map(purchase, PurchaseDto.class))
                .collect(Collectors.toList());
    }

    private BigDecimal calculateAddingScore(PurchaseDto purchaseDto) {
        BigDecimal sum = purchaseDto.getSum();
        return sum.divide(BigDecimal.valueOf(10));
    }

    private Pageable getPaging(Integer pageNumber, Integer pageSize) {
        return PageRequest.of(pageNumber - 1, pageSize, Sort.by(Sort.Order.by("id")));
    }
}
