package com.senla.service.impl;

import com.senla.model.DiscountPolicy;
import com.senla.repository.DiscountPolicyRepository;
import com.senla.service.DiscountPolicyService;
import com.senla.service.dto.DiscountPolicyDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountPolicyServiceImpl implements DiscountPolicyService {
    private final ModelMapper modelMapper;
    private final DiscountPolicyRepository discountPolicyRepository;

    @Override
    public void save(DiscountPolicyDto discountPolicyDto) {
        DiscountPolicy discountPolicy = modelMapper.map(discountPolicyDto, DiscountPolicy.class);
        discountPolicyRepository.save(discountPolicy);

    }

    @Override
    public DiscountPolicyDto findById(Long id) {
        DiscountPolicy discountPolicy = discountPolicyRepository.findById(id);
        return modelMapper.map(discountPolicy, DiscountPolicyDto.class);
    }

    @Override
    public List<DiscountPolicyDto> findAll() {
        return discountPolicyRepository.findAll().stream()
                .map(discountPolicy -> modelMapper.map(discountPolicy, DiscountPolicyDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(DiscountPolicyDto discountPolicyDto) {
        DiscountPolicy discountPolicy = modelMapper.map(discountPolicyDto, DiscountPolicy.class);
        discountPolicyRepository.delete(discountPolicy);
        return discountPolicyRepository.findById(discountPolicyDto.getId()) == null;
    }

    @Override
    public void update(DiscountPolicyDto discountPolicyDto) {
        DiscountPolicy discountPolicy = modelMapper.map(discountPolicyDto, DiscountPolicy.class);
        discountPolicyRepository.update(discountPolicy);
    }
}
