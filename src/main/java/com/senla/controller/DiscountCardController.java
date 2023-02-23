package com.senla.controller;

import com.senla.model.dto.DiscountCardDto;
import com.senla.service.DiscountCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cards")
public class DiscountCardController {

    private final DiscountCardService cardService;

    @GetMapping
    public List<DiscountCardDto> getAllCards() {
        return cardService.findAll();
    }

    @GetMapping(value = "/{id}")
    public DiscountCardDto getCardById(@PathVariable(name = "id") Long cardId) {
        return cardService.findById(cardId);
    }


    @GetMapping(value = "/user/{id}")
    public List<DiscountCardDto> getCardByUserId(@PathVariable(name = "id") Long userId) {
        return cardService.getCardsByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<DiscountCardDto> createNewCard(@RequestBody DiscountCardDto newCard) {
        cardService.save(newCard);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(newCard.getName())
                .toUri());
        return new ResponseEntity<>(newCard, headers, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<DiscountCardDto> updateCard(@RequestBody DiscountCardDto discountCard) {
        cardService.update(discountCard);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(discountCard.getName())
                .toUri());
        return new ResponseEntity<>(discountCard, headers, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCard(@PathVariable(name = "id") Long discountCardId) {
        cardService.delete(discountCardId);
        return new ResponseEntity<>("Card deleted successfully.", HttpStatus.ACCEPTED);
    }
}
