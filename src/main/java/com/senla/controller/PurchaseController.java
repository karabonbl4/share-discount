package com.senla.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.service.PurchaseService;
import com.senla.service.dto.PurchaseDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public String findById(String id){
        long l = Long.parseLong(id);
        PurchaseDto byId = purchaseService.findById(l);
        return objectMapper.writeValueAsString(byId);
    }
}
