package com.senla.service.impl;

import com.senla.dao.DiscountCardRepository;
import com.senla.exceptions.NotFoundException;
import com.senla.model.entity.DiscountCard;
import com.senla.service.DiscountCardService;
import com.senla.model.dto.DiscountCardDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DiscountCardServiceImpl implements DiscountCardService {
    private final ModelMapper modelMapper;
    private final DiscountCardRepository discountCardRepository;

    @Override
    public DiscountCardDto save(DiscountCardDto discountCardDto) {
        DiscountCard discountCard = modelMapper.map(discountCardDto, DiscountCard.class);
        DiscountCard savedCard = discountCardRepository.save(discountCard);
        return modelMapper.map(savedCard, DiscountCardDto.class);
    }

    @Override
    public DiscountCardDto findById(Long id) throws NotFoundException{
        DiscountCard discountCard = discountCardRepository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(discountCard, DiscountCardDto.class);
    }

    @Override
    public List<DiscountCardDto> findAll() {
        return discountCardRepository.findAll().stream()
                .map(discountCard -> modelMapper.map(discountCard, DiscountCardDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        DiscountCard deletedCard = discountCardRepository.getReferenceById(id);
        discountCardRepository.delete(deletedCard);
    }

    @Override
    public DiscountCardDto update(DiscountCardDto discountCardDto) {
        DiscountCard discountCard = modelMapper.map(discountCardDto, DiscountCard.class);
        DiscountCard returnedCard = discountCardRepository.saveAndFlush(discountCard);
        return modelMapper.map(returnedCard, DiscountCardDto.class);
    }

    @Override
    public List<DiscountCardDto> getCardsByUserId(Long userId) {
        return discountCardRepository.findByOwner_Id(userId).stream()
                .map(discountCard -> modelMapper.map(discountCard, DiscountCardDto.class))
                .collect(Collectors.toList());
    }
}
