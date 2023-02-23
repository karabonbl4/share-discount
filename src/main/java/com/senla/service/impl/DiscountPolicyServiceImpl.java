package com.senla.service.impl;

import com.senla.dao.DiscountPolicyRepository;
import com.senla.exceptions.EntityNotFoundException;
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
    public DiscountPolicyDto save(DiscountPolicyDto discountPolicyDto) {
        DiscountPolicy discountPolicy = modelMapper.map(discountPolicyDto, DiscountPolicy.class);
        DiscountPolicy returnedSavePolicy = discountPolicyRepository.save(discountPolicy);
        return modelMapper.map(returnedSavePolicy, DiscountPolicyDto.class);
    }

    @Override
    public DiscountPolicyDto findById(Long id) throws EntityNotFoundException {
        DiscountPolicy discountPolicy = discountPolicyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
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
        DiscountPolicy policyById = discountPolicyRepository.getReferenceById(discountPolicyId);
        discountPolicyRepository.delete(policyById);
    }

    @Override
    public DiscountPolicyDto update(DiscountPolicyDto discountPolicyDto) {
        DiscountPolicy discountPolicy = modelMapper.map(discountPolicyDto, DiscountPolicy.class);
        DiscountPolicy returnedSavePolicy = discountPolicyRepository.saveAndFlush(discountPolicy);
        return modelMapper.map(returnedSavePolicy, DiscountPolicyDto.class);
    }

    @Override
    public List<DiscountPolicyDto> findByTrademarkId(Long id) {
        return discountPolicyRepository.findByTrademark_Id(id).stream()
                .map(discountPolicy -> modelMapper.map(discountPolicy, DiscountPolicyDto.class))
                .collect(Collectors.toList());
    }
}
