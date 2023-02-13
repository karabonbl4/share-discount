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
    public void save(DiscountCardDto discountCardDto) {
        DiscountCard discountCard = modelMapper.map(discountCardDto, DiscountCard.class);
        discountCardRepository.save(discountCard);
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
        DiscountCardDto deletedCard = findById(id);
        DiscountCard discountCard = modelMapper.map(deletedCard, DiscountCard.class);
        discountCardRepository.delete(discountCard);
    }

    @Override
    public void update(DiscountCardDto discountCardDto) {
        DiscountCard discountCard = modelMapper.map(discountCardDto, DiscountCard.class);
        discountCardRepository.saveAndFlush(discountCard);
    }

    @Override
    public List<DiscountCardDto> getCardsByUserId(Long userId) {
        return discountCardRepository.findByOwner_Id(userId).stream()
                .map(discountCard -> modelMapper.map(discountCard, DiscountCardDto.class))
                .collect(Collectors.toList());
    }
}
