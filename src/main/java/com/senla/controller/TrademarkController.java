package com.senla.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.service.TrademarkService;
import com.senla.service.dto.TrademarkDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TrademarkController {

    private final TrademarkService trademarkService;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public String findById(String id){
        Long l = Long.parseLong(id);
        TrademarkDto trademarkDto = trademarkService.findById(l);
        return objectMapper.writeValueAsString(trademarkDto);
    }
    @SneakyThrows
    public void save(String newTrademark){
        TrademarkDto newDto = objectMapper.readValue(newTrademark, TrademarkDto.class);
        trademarkService.save(newDto);
    }
    @SneakyThrows
    public void update(String newTrademark){
        TrademarkDto newDto = objectMapper.readValue(newTrademark, TrademarkDto.class);
        trademarkService.update(newDto);
    }

}
