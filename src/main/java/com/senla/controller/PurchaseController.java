package com.senla.controller;

import com.senla.service.PurchaseService;
import com.senla.model.dto.PurchaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping
    public List<PurchaseDto> getAllPurchases(@Param(value = "pageNumber") Integer pageNumber,
                                             @Param(value = "pageSize") Integer pageSize) {
        return purchaseService.findAll(pageNumber, pageSize);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping(value = "/{id}")
    public PurchaseDto getById(@PathVariable Long id) {
        return purchaseService.findById(id);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping(value = "/user/{id}")
    public List<PurchaseDto> getPurchasesByUserId(@PathVariable(name = "id") Long userId,
                                                  @Param(value = "pageNumber") Integer pageNumber,
                                                  @Param(value = "pageSize") Integer pageSize) {
        return purchaseService.findByUserId(userId, pageNumber, pageSize);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping(value = "/card/{id}")
    public List<PurchaseDto> getPurchasesByCardId(@PathVariable(name = "id") Long cardId,
                                                  @Param(value = "pageNumber") Integer pageNumber,
                                                  @Param(value = "pageSize") Integer pageSize) {
        return purchaseService.findByCardId(cardId, pageNumber, pageSize);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping(value = "/trademark/{id}")
    public List<PurchaseDto> getPurchasesByTrademarkId(@PathVariable(name = "id") Long trademarkId,
                                                       @Param(value = "pageNumber") Integer pageNumber,
                                                       @Param(value = "pageSize") Integer pageSize) {
        return purchaseService.findByTrademarkId(trademarkId, pageNumber, pageSize);
    }

    @Secured(value = "ROLE_SERVICE")
    @PostMapping
    public ResponseEntity<PurchaseDto> createPurchase(@RequestBody PurchaseDto newPurchase) {
        purchaseService.save(newPurchase);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(newPurchase.getName())
                .toUri());
        return new ResponseEntity<>(newPurchase, headers, HttpStatus.CREATED);
    }
}
