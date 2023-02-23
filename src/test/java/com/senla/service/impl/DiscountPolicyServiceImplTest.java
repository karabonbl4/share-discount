package com.senla.service.impl;

import com.senla.dao.DiscountPolicyRepository;
import com.senla.model.dto.DiscountPolicyDto;
import com.senla.model.entity.DiscountPolicy;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiscountPolicyServiceImplTest {

    @Mock
    private DiscountPolicyRepository discountPolicyRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private DiscountPolicyServiceImpl discountPolicyService;
    private final static Long ID = 1L;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSavePolicySuccessfully() {
        final DiscountPolicyDto policyDto = mock(DiscountPolicyDto.class);
        final DiscountPolicy mapedPolicy = mock(DiscountPolicy.class);
        when(modelMapper.map(policyDto, DiscountPolicy.class)).thenReturn(mapedPolicy);
        when(discountPolicyRepository.save(mapedPolicy)).thenReturn(mapedPolicy);
        when(modelMapper.map(mapedPolicy, DiscountPolicyDto.class)).thenReturn(policyDto);

        DiscountPolicyDto actualPolicy = discountPolicyService.save(policyDto);

        assertNotNull(actualPolicy);
        assertEquals(policyDto, actualPolicy);
        verify(discountPolicyRepository).save(mapedPolicy);
    }

    @Test
    public void shouldFindByIdPolicySuccessfully() {
        final DiscountPolicyDto policyDto = mock(DiscountPolicyDto.class);
        final DiscountPolicy mapedPolicy = mock(DiscountPolicy.class);
        when(modelMapper.map(policyDto, DiscountPolicy.class)).thenReturn(mapedPolicy);
        when(discountPolicyRepository.findById(ID)).thenReturn(Optional.ofNullable(mapedPolicy));
        when(modelMapper.map(mapedPolicy, DiscountPolicyDto.class)).thenReturn(policyDto);

        final DiscountPolicyDto actual = discountPolicyService.findById(ID);

        assertNotNull(actual);
        assertEquals(policyDto, actual);
        verify(discountPolicyRepository).findById(ID);
    }

    @Test
    public void shouldFindAllPoliciesSuccessfully() {
        discountPolicyService.findAll();

        verify(discountPolicyRepository).findAll();
    }

    @Test
    public void shouldDeletePolicySuccessfully() {
        final DiscountPolicy mapedPolicy = mock(DiscountPolicy.class);
        when(discountPolicyRepository.getReferenceById(ID)).thenReturn(mapedPolicy);

        discountPolicyService.delete(ID);

        verify(discountPolicyRepository).delete(mapedPolicy);
    }

    @Test
    public void shouldUpdatePolicySuccessfully() {
        final DiscountPolicyDto policyDto = mock(DiscountPolicyDto.class);
        final DiscountPolicy mapedPolicy = mock(DiscountPolicy.class);
        when(modelMapper.map(policyDto, DiscountPolicy.class)).thenReturn(mapedPolicy);
        when(discountPolicyRepository.saveAndFlush(mapedPolicy)).thenReturn(mapedPolicy);
        when(modelMapper.map(mapedPolicy, DiscountPolicyDto.class)).thenReturn(policyDto);

        DiscountPolicyDto actualPolicy = discountPolicyService.update(policyDto);

        assertNotNull(actualPolicy);
        assertEquals(policyDto, actualPolicy);
        verify(discountPolicyRepository).saveAndFlush(mapedPolicy);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getPoliciesByTrademarkId() {
        List<DiscountPolicy> policies = mock(List.class);
        when(discountPolicyRepository.findByTrademark_Id(ID)).thenReturn(policies);
        discountPolicyService.findByTrademarkId(ID);

        verify(discountPolicyRepository).findByTrademark_Id(ID);
    }
}