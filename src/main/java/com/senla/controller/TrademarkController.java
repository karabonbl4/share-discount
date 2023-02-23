package com.senla.controller;

import com.senla.model.dto.TrademarkDto;
import com.senla.service.TrademarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/trademarks")
public class TrademarkController {
    private final TrademarkService trademarkService;

    @GetMapping
    public List<TrademarkDto> getAllTrademarks() {
        return trademarkService.findAll();
    }

    @GetMapping(value = "/{id}")
    public TrademarkDto getById(@PathVariable(name = "id") Long trademarkId) {
        return trademarkService.findById(trademarkId);
    }

    @PostMapping
    public ResponseEntity<TrademarkDto> createTrademark(@RequestBody TrademarkDto newTrademark) {
        trademarkService.save(newTrademark);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(newTrademark.getTitle())
                .toUri());
        return new ResponseEntity<>(newTrademark, headers, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<TrademarkDto> updateTrademark(@RequestBody TrademarkDto trademarkUpd) {
        trademarkService.update(trademarkUpd);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(trademarkUpd.getTitle())
                .toUri());
        return new ResponseEntity<>(trademarkUpd, headers, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteTrademark(@PathVariable(name = "id") Long trademarkId) {
        trademarkService.delete(trademarkId);
        return new ResponseEntity<>("Trademark deleted successfully.", HttpStatus.ACCEPTED);
    }
}
