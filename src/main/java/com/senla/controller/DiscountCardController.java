package com.senla.controller;

import com.senla.model.dto.DiscountCardDto;
import com.senla.model.dto.rent.DiscountCardForRentDto;
import com.senla.model.dto.save.DiscountCardWithTrademarkDto;
import com.senla.service.DiscountCardService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cards")
public class DiscountCardController {

    private final DiscountCardService cardService;

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping
    public List<DiscountCardDto> getAllCardsWithSort(@Param(value = "pageNumber") Integer pageNumber,
                                                     @Param(value = "pageSize") Integer pageSize,
                                                     @Param(value = "sortingBy") String[] sortingBy) {
        return cardService.findAllWithSort(pageNumber, pageSize, sortingBy);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping(value = "/{id}")
    public DiscountCardDto getCardById(@PathVariable(name = "id") Long cardId) {
        return cardService.getById(cardId);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping(value = "/user/{id}")
    public List<DiscountCardDto> getCardByUserId(@PathVariable(value = "id") Long userId,
                                                 @Param(value = "pageNumber") Integer pageNumber,
                                                 @Param(value = "pageSize") Integer pageSize,
                                                 @Param(value = "sortingBy") @NotNull String[] sortingBy) {
        return cardService.getCardsByUserId(userId, pageNumber, pageSize, sortingBy);
    }

    @Secured(value = "ROLE_USER")
    @GetMapping(value = "/my")
    public List<DiscountCardDto> getMyCards(@Param(value = "pageNumber") Integer pageNumber,
                                            @Param(value = "pageSize") Integer pageSize,
                                            @Param(value = "sortingBy") @NotNull String[] sortingBy){
        return cardService.getMyCards(pageNumber, pageSize, sortingBy);
    }

    @Secured(value = "ROLE_USER")
    @GetMapping(value = "/confirmation")
    public DiscountCardWithTrademarkDto getDiscountCardForSaveFromLink(@RequestParam(value = "card") String cardFromLink) {
        return cardService.getCardFromLink(cardFromLink, DiscountCardWithTrademarkDto.class);
    }

    @Secured(value = "ROLE_USER")
    @GetMapping(value = "/rent/request")
    public DiscountCardForRentDto getDiscountCardForRentFromLink(@RequestParam(value = "card") String cardFromLink) {
        return cardService.getCardFromLink(cardFromLink, DiscountCardForRentDto.class);
    }

    @Secured(value = "ROLE_USER")
    @GetMapping(value = "/trademark/{id}")
    public List<DiscountCardDto>  getCardsForAdminTrademark(@PathVariable(value = "id") Long trademarkId,
                                                            @Param(value = "pageNumber") Integer pageNumber,
                                                            @Param(value = "pageSize") Integer pageSize,
                                                            @Param(value = "sortingBy")String[] sortingBy){
        return cardService.getCardsForAdminTrademark(trademarkId, pageNumber, pageSize, sortingBy);
    }

    @Secured(value = "ROLE_USER")
    @PostMapping(value = "/rent/request/{id}")
    public ResponseEntity<String> sendRentRequest (@PathVariable(value = "id") Long cardId,
                                                   @RequestParam(value = "startRent") String startRent,
                                                   @RequestParam(value = "endRent") String endRent){
        String response = cardService.sendRequestForRentCard(cardId, startRent, endRent);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @Secured(value = "ROLE_USER")
    @PostMapping
    public ResponseEntity<DiscountCardDto> createNewCard(@RequestBody DiscountCardWithTrademarkDto newCard) {
        DiscountCardDto savedCard = cardService.save(newCard);
        return new ResponseEntity<>(savedCard, HttpStatus.CREATED);
    }

    @Secured(value = "ROLE_USER")
    @PostMapping(value = "/confirmation")
    public ResponseEntity<String> sendSaveRequest(@RequestBody DiscountCardWithTrademarkDto newCard){
        String resultOk = cardService.sendRequestForSaveCard(newCard);
        return new ResponseEntity<>(resultOk, HttpStatus.OK);
    }

    @Secured(value = "ROLE_USER")
    @PutMapping(value = "/confirmation")
    public ResponseEntity<DiscountCardDto> confirmCardForSave(@RequestBody DiscountCardWithTrademarkDto confirmedCard) {
        DiscountCardDto cardDto = cardService.confirmDiscountCardToSave(confirmedCard);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentContextPath().path("/cards/{id}")
                .buildAndExpand(cardDto.getId())
                .toUri());
        return new ResponseEntity<>(cardDto, headers, HttpStatus.ACCEPTED);
    }

    @Secured(value = "ROLE_USER")
    @PutMapping(value = "/rent/request")
    public ResponseEntity<DiscountCardForRentDto> confirmRentCard(@RequestBody DiscountCardForRentDto rentConfirmedCard){
        DiscountCardForRentDto cardDto = cardService.confirmRentCard(rentConfirmedCard);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentContextPath().path("/cards/{id}")
                .buildAndExpand(cardDto.getId())
                .toUri());
        return new ResponseEntity<>(cardDto, headers, HttpStatus.ACCEPTED);
    }

    @PutMapping
    public ResponseEntity<DiscountCardDto> updateCard(@RequestBody DiscountCardWithTrademarkDto discountCard) {
        DiscountCardDto updated = cardService.update(discountCard);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(discountCard.getName())
                .toUri());
        return new ResponseEntity<>(updated, headers, HttpStatus.ACCEPTED);
    }

    @Secured(value = "ROLE_ADMIN")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCard(@PathVariable(name = "id") Long discountCardId) {
        cardService.delete(discountCardId);
        return new ResponseEntity<>("Card is deleted successfully.", HttpStatus.ACCEPTED);
    }
}
