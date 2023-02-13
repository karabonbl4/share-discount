package com.senla.service.impl;

import com.senla.dao.DiscountPolicyRepository;
import com.senla.exceptions.NotFoundException;
import com.senla.model.entity.DiscountPolicy;
import com.senla.service.DiscountPolicyService;
import com.senla.model.dto.DiscountPolicyDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DiscountPolicyServiceImpl implements DiscountPolicyService {
    private final ModelMapper modelMapper;
    private final DiscountPolicyRepository discountPolicyRepository;

    @Override
    public void save(DiscountPolicyDto discountPolicyDto) {
        DiscountPolicy discountPolicy = modelMapper.map(discountPolicyDto, DiscountPolicy.class);
        discountPolicyRepository.save(discountPolicy);

    }

    @Override
    public DiscountPolicyDto findById(Long id) throws NotFoundException{
        DiscountPolicy discountPolicy = discountPolicyRepository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(discountPolicy, DiscountPolicyDto.class);
    }

    @Override
    public List<DiscountPolicyDto> findAll() {
        return discountPolicyRepository.findAll().stream()
                .map(discountPolicy -> modelMapper.map(discountPolicy, DiscountPolicyDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long discountPolicyId) {
        DiscountPolicyDto discountPolicyDto = findById(discountPolicyId);
        DiscountPolicy discountPolicy = modelMapper.map(discountPolicyDto, DiscountPolicy.class);
        discountPolicyRepository.delete(discountPolicy);
    }

    @Override
    public void update(DiscountPolicyDto discountPolicyDto) {
        DiscountPolicy discountPolicy = modelMapper.map(discountPolicyDto, DiscountPolicy.class);
        discountPolicyRepository.saveAndFlush(discountPolicy);
    }

    @Override
    public List<DiscountPolicyDto> findByTrademarkId(Long id) {
        return discountPolicyRepository.findByTrademark_Id(id).stream()
                .map(discountPolicy -> modelMapper.map(discountPolicy, DiscountPolicyDto.class))
                .collect(Collectors.toList());
    }
}
