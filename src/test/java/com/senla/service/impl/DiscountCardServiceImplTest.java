package com.senla.service.impl;

import com.senla.dao.DiscountCardRepository;
import com.senla.model.dto.DiscountCardDto;
import com.senla.model.dto.RoleDto;
import com.senla.model.dto.UserDto;
import com.senla.model.dto.mapper.CustomDiscountCardMapper;
import com.senla.model.dto.save.DiscountCardWithTrademarkDto;
import com.senla.model.entity.DiscountCard;
import com.senla.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
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
    @Mock
    private CustomDiscountCardMapper customDiscountCardMapper;
    @Mock
    private UserService userService;
    @InjectMocks
    private DiscountCardServiceImpl service;

    private final static Long ID = 1L;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void shouldSaveCardSuccessfully() {
//        final DiscountCardWithTrademarkDto cardDto = mock(DiscountCardWithTrademarkDto.class);
//        final DiscountCard mapedCard = mock(DiscountCard.class);
//        final DiscountCardDto returnedCard = mock(DiscountCardDto.class);
//        when(customDiscountCardMapper.map(cardDto)).thenReturn(mapedCard);
//        when(repository.save(mapedCard)).thenReturn(mapedCard);
//        when(modelMapper.map(mapedCard, DiscountCardDto.class)).thenReturn(returnedCard);
//        DiscountCardDto savedCard = service.save(cardDto);
//
//        assertNotNull(savedCard);
//        assertEquals(returnedCard, savedCard);
//
//        verify(repository).save(mapedCard);
//    }

    @Test
    public void shouldFindByIdCardSuccessfully() {
        final DiscountCardDto cardDto = mock(DiscountCardDto.class);
        final DiscountCard mapedCard = mock(DiscountCard.class);
        when(modelMapper.map(cardDto, DiscountCard.class)).thenReturn(mapedCard);
        when(repository.findById(ID)).thenReturn(Optional.ofNullable(mapedCard));
        when(modelMapper.map(mapedCard, DiscountCardDto.class)).thenReturn(cardDto);

        final DiscountCardDto actual = service.getById(ID);

        assertNotNull(actual);
        assertEquals(cardDto, actual);
        verify(repository).findById(ID);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldFindAllCardsSuccessfully() {
        List<DiscountCard> cards = mock(List.class);
        List<DiscountCardDto> expectedCards = mock(List.class);
        UserDto userDto = new UserDto();
        userDto.setRoles(Collections.singletonList(new RoleDto("ROLE_ADMIN")));
        Pageable paging = PageRequest.of(0, 5, Sort.by("discount"));
        when(userService.getAuthUser()).thenReturn(userDto);
        when(repository.findAllWithSortForAdmin(paging)).thenReturn(cards);

        List<DiscountCardDto> allWithSort = service.findAllWithSort(1, 5, new String[]{"discount"});

        assertNotNull(allWithSort);
        assertEquals(allWithSort.size(), expectedCards.size());
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
        final DiscountCardWithTrademarkDto cardDto = mock(DiscountCardWithTrademarkDto.class);
        final DiscountCard mapedCard = mock(DiscountCard.class);
        final DiscountCardDto returnedCard = mock(DiscountCardDto.class);
        when(customDiscountCardMapper.map(cardDto)).thenReturn(mapedCard);
        when(repository.saveAndFlush(mapedCard)).thenReturn(mapedCard);
        when(modelMapper.map(mapedCard, DiscountCardDto.class)).thenReturn(returnedCard);

        DiscountCardDto actualCard = service.update(cardDto);

        assertNotNull(actualCard);
        assertEquals(returnedCard, actualCard);
        verify(repository).saveAndFlush(mapedCard);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getCardsByUserId() {
        List<DiscountCard> cards = mock(List.class);
        Pageable paging = PageRequest.of(0, 5, Sort.by("discount"));
        when(repository.findByOwner_Id(ID, paging)).thenReturn(cards);
        service.getCardsByUserId(ID, 1, 5, new String[]{"discount"});

        verify(repository).findByOwner_Id(ID, paging);
    }
}