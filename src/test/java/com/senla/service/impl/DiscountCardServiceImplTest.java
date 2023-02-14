package com.senla.service.impl;

import com.senla.dao.DiscountCardRepository;
import com.senla.model.dto.DiscountCardDto;
import com.senla.model.entity.DiscountCard;
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
public class DiscountCardServiceImplTest {

    @Mock
    private DiscountCardRepository repository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private DiscountCardServiceImpl service;

    private final static Long ID = 1L;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSaveCardSuccessfully() {
        final DiscountCardDto cardDto = mock(DiscountCardDto.class);
        final DiscountCard mapedCard = mock(DiscountCard.class);
        when(modelMapper.map(cardDto, DiscountCard.class)).thenReturn(mapedCard);
        service.save(cardDto);

        verify(repository).save(mapedCard);
    }

    @Test
    public void shouldFindByIdCardSuccessfully() {
        final DiscountCardDto cardDto = mock(DiscountCardDto.class);
        final DiscountCard mapedCard = mock(DiscountCard.class);
        when(modelMapper.map(cardDto, DiscountCard.class)).thenReturn(mapedCard);
        when(repository.findById(ID)).thenReturn(Optional.ofNullable(mapedCard));
        when(modelMapper.map(mapedCard, DiscountCardDto.class)).thenReturn(cardDto);

        final DiscountCardDto actual = service.findById(ID);

        assertNotNull(actual);
        assertEquals(cardDto, actual);
        verify(repository).findById(ID);
    }

    @Test
    public void shouldFindAllCardsSuccessfully() {
        service.findAll();

        verify(repository).findAll();
    }

    @Test
    public void shouldDeleteCardSuccessfully() {
        final DiscountCard mapedCard = mock(DiscountCard.class);
        when(repository.getReferenceById(ID)).thenReturn(mapedCard);

        service.delete(ID);

        verify(repository).delete(mapedCard);
    }

    @Test
    public void shouldUpdateCardSuccessfully() {
        final DiscountCardDto cardDto = mock(DiscountCardDto.class);
        final DiscountCard mapedCard = mock(DiscountCard.class);
        when(modelMapper.map(cardDto, DiscountCard.class)).thenReturn(mapedCard);
        when(repository.saveAndFlush(mapedCard)).thenReturn(mapedCard);
        when(modelMapper.map(mapedCard, DiscountCardDto.class)).thenReturn(cardDto);

        DiscountCardDto actualCard = service.update(cardDto);

        assertNotNull(actualCard);
        assertEquals(cardDto, actualCard);
        verify(repository).saveAndFlush(mapedCard);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getCardsByUserId() {
        List<DiscountCard> cards = mock(List.class);
        when(repository.findByOwner_Id(ID)).thenReturn(cards);
        service.getCardsByUserId(ID);

        verify(repository).findByOwner_Id(ID);
    }
}