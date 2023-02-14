package com.senla.service.impl;

import com.senla.dao.DiscountCardRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DiscountCardServiceImplTest {

    @Mock
    private DiscountCardRepository repository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private DiscountCardServiceImpl service;
    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void save() {

    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void getCardsByUserId() {
    }
}