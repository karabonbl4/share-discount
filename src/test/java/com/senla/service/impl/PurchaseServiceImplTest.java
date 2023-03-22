package com.senla.service.impl;

import com.senla.dao.PurchaseRepository;
import com.senla.model.dto.PurchaseDto;
import com.senla.model.entity.Purchase;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceImplTest {
    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private PurchaseServiceImpl purchaseService;
    private static final Long ID = 1L;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


//    @Test
//    public void shouldSavePurchaseSuccessfully() {
//        final PurchaseDto purchaseDto = mock(PurchaseDto.class);
//        final Purchase mapedPurchase = mock(Purchase.class);
//        when(modelMapper.map(purchaseDto, Purchase.class)).thenReturn(mapedPurchase);
//        when(purchaseRepository.save(mapedPurchase)).thenReturn(mapedPurchase);
//        when(modelMapper.map(mapedPurchase, PurchaseDto.class)).thenReturn(purchaseDto);
//
//        PurchaseDto actualPurchase = purchaseService.save(purchaseDto);
//
//        assertNotNull(actualPurchase);
//        assertEquals(purchaseDto, actualPurchase);
//        verify(purchaseRepository).save(mapedPurchase);
//    }

    @Test
    public void shouldFindByIdPurchaseSuccessfully() {
        final PurchaseDto purchaseDto = mock(PurchaseDto.class);
        final Purchase mapedPurchase = mock(Purchase.class);
        when(modelMapper.map(purchaseDto, Purchase.class)).thenReturn(mapedPurchase);
        when(purchaseRepository.findById(ID)).thenReturn(Optional.ofNullable(mapedPurchase));
        when(modelMapper.map(mapedPurchase, PurchaseDto.class)).thenReturn(purchaseDto);

        final PurchaseDto actual = purchaseService.findById(ID);

        assertNotNull(actual);
        assertEquals(purchaseDto, actual);
        verify(purchaseRepository).findById(ID);
    }

    @Test
    public void shouldFindAllPurchasesSuccessfully() {
        purchaseService.findAll(1, 100);

        verify(purchaseRepository).findAll(PageRequest.of(1, 100));
    }

//    @Test
//    public void shouldUpdatePurchaseSuccessfully() {
//        final PurchaseDto purchaseDto = mock(PurchaseDto.class);
//        final Purchase mapedPurchase = mock(Purchase.class);
//        when(modelMapper.map(purchaseDto, Purchase.class)).thenReturn(mapedPurchase);
//        when(purchaseRepository.saveAndFlush(mapedPurchase)).thenReturn(mapedPurchase);
//        when(modelMapper.map(mapedPurchase, PurchaseDto.class)).thenReturn(purchaseDto);
//
//        PurchaseDto actualPolicy = purchaseService.update(purchaseDto);
//
//        assertNotNull(actualPolicy);
//        assertEquals(purchaseDto, actualPolicy);
//        verify(purchaseRepository).saveAndFlush(mapedPurchase);
//    }

    @SuppressWarnings("unchecked")
    @Test
    public void getPurchasesByCardId() {
        List<Purchase> purchases = mock(List.class);
        when(purchaseRepository.findByCard_Id(ID, PageRequest.of(1, 100))).thenReturn(purchases);
        purchaseService.findByCardId(ID, 1, 100);

        verify(purchaseRepository).findByCard_Id(ID, PageRequest.of(1, 100));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getPurchasesByUserId() {
        List<Purchase> purchases = mock(List.class);
        when(purchaseRepository.findByBuyer_Id(ID, PageRequest.of(1, 100))).thenReturn(purchases);
        purchaseService.findByUserId(ID, 1, 100);

        verify(purchaseRepository).findByBuyer_Id(ID, PageRequest.of(1, 100));
    }
}