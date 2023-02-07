package com.senla.service.impl;

import com.senla.model.DiscountCard;
import com.senla.repository.DiscountCardRepository;
import com.senla.service.DiscountCardService;
import com.senla.service.dto.DiscountCardDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountCardServiceImpl implements DiscountCardService {
    private final ModelMapper modelMapper;
    private final DiscountCardRepository discountCardRepository;

    @Override
    public void save(DiscountCardDto discountCardDto) {
        DiscountCard discountCard = modelMapper.map(discountCardDto, DiscountCard.class);
        discountCardRepository.save(discountCard);
    }

    @Override
    public DiscountCardDto findById(Long id) {
        DiscountCard discountCard = discountCardRepository.findById(id);
        return modelMapper.map(discountCard, DiscountCardDto.class);
    }

    @Override
    public List<DiscountCardDto> findAll() {
        return discountCardRepository.findAll().stream()
                .map(discountCard -> modelMapper.map(discountCard, DiscountCardDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(DiscountCardDto discountCardDto) {
        DiscountCard discountCard = modelMapper.map(discountCardDto, DiscountCard.class);
        discountCardRepository.delete(discountCard);
    }

    @Override
    public void update(DiscountCardDto discountCardDto) {
        DiscountCard discountCard = modelMapper.map(discountCardDto, DiscountCard.class);
        discountCardRepository.update(discountCard);
    }
}
