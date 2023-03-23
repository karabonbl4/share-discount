package com.senla.service.impl;

import com.senla.dao.TrademarkRepository;
import com.senla.model.dto.TrademarkDto;
import com.senla.model.entity.Trademark;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrademarkServiceImplTest {

    @Mock
    private TrademarkRepository trademarkRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private TrademarkServiceImpl trademarkService;
    private static final Long ID = 1L;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSaveTrademarkSuccessfully() {
        final TrademarkDto trademarkDto = mock(TrademarkDto.class);
        final Trademark mapedTrademark = mock(Trademark.class);
        when(modelMapper.map(trademarkDto, Trademark.class)).thenReturn(mapedTrademark);
        when(trademarkRepository.save(mapedTrademark)).thenReturn(mapedTrademark);
        when(modelMapper.map(mapedTrademark, TrademarkDto.class)).thenReturn(trademarkDto);

        TrademarkDto actualTrademark = trademarkService.save(trademarkDto);

        assertNotNull(actualTrademark);
        assertEquals(trademarkDto, actualTrademark);
        verify(trademarkRepository).save(mapedTrademark);
    }

    @Test
    public void shouldFindByIdTrademarkSuccessfully() {
        final TrademarkDto trademarkDto = mock(TrademarkDto.class);
        final Trademark mapedTrademark = mock(Trademark.class);
        when(modelMapper.map(trademarkDto, Trademark.class)).thenReturn(mapedTrademark);
        when(trademarkRepository.findById(ID)).thenReturn(Optional.ofNullable(mapedTrademark));
        when(modelMapper.map(mapedTrademark, TrademarkDto.class)).thenReturn(trademarkDto);

        TrademarkDto actual = trademarkService.findById(ID);

        assertNotNull(actual);
        assertEquals(trademarkDto, actual);
        verify(trademarkRepository).findById(ID);
    }

    @Test
    public void shouldFindAllTrademarksSuccessfully() {
        trademarkService.findAll(1, 100);

        verify(trademarkRepository).findAll();
    }

    @Test
    public void shouldDeleteTrademarkSuccessfully() {
        final Trademark mapedTrademark = mock(Trademark.class);
        when(trademarkRepository.getReferenceById(ID)).thenReturn(mapedTrademark);

        trademarkService.delete(ID);

        verify(trademarkRepository).delete(mapedTrademark);
    }

    @Test
    public void shouldUpdateTrademarkSuccessfully() {
        final TrademarkDto trademarkDto = mock(TrademarkDto.class);
        final Trademark mapedTrademark = mock(Trademark.class);
        when(modelMapper.map(trademarkDto, Trademark.class)).thenReturn(mapedTrademark);
        when(trademarkRepository.saveAndFlush(mapedTrademark)).thenReturn(mapedTrademark);
        when(modelMapper.map(mapedTrademark, TrademarkDto.class)).thenReturn(trademarkDto);

        TrademarkDto actualTrademark = trademarkService.update(trademarkDto);

        assertNotNull(actualTrademark);
        assertEquals(trademarkDto, actualTrademark);
        verify(trademarkRepository).saveAndFlush(mapedTrademark);
    }
}