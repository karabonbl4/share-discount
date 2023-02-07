package com.senla.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.service.DiscountCardService;
import com.senla.service.dto.DiscountCardDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DiscountCardController {
    private final ObjectMapper objectMapper;
    private final DiscountCardService discountCardService;
    @SneakyThrows
    public String findById(String id){
        Long l = Long.parseLong(id);
        DiscountCardDto discountCardDto = discountCardService.findById(l);
        return objectMapper.writeValueAsString(discountCardDto);
    }

    @SneakyThrows
    public void create(String newDiscountCard){
        DiscountCardDto newDiscountCardDto = objectMapper.readValue(newDiscountCard, DiscountCardDto.class);
        discountCardService.save(newDiscountCardDto);
    }

    @SneakyThrows
    public void delete(String discountCard){
        DiscountCardDto DiscountCardDto = objectMapper.readValue(discountCard, DiscountCardDto.class);
        discountCardService.delete(DiscountCardDto);
    }

    @SneakyThrows
    public void update(String discountCard){
        DiscountCardDto discountCardDto = objectMapper.readValue(discountCard, DiscountCardDto.class);
        discountCardService.update(discountCardDto);
    }
}
