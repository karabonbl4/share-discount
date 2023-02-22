package com.senla.controller;

import com.senla.service.PurchaseService;
import com.senla.model.dto.PurchaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping
    public List<PurchaseDto> getAllPurchases() {
        return purchaseService.findAll();
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(value = "/{id}")
    public PurchaseDto getById(@PathVariable Long id) {
        return purchaseService.findById(id);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(value = "/user/{id}")
    public List<PurchaseDto> getPurchasesByUserId(@PathVariable(name = "id") Long userId) {
        return purchaseService.findByUserId(userId);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(value = "/card/{id}")
    public List<PurchaseDto> getPurchasesByCardId(@PathVariable(name = "id") Long cardId) {
        return purchaseService.findByCardId(cardId);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping(value = "/create")
    public ResponseEntity<?> createPurchase(@RequestBody PurchaseDto newPurchase) {
        purchaseService.save(newPurchase);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(newPurchase.getName())
                .toUri());
        return new ResponseEntity<>(newPurchase, headers, HttpStatus.CREATED);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?> deletePurchase(@PathVariable(name = "id") Long purchaseId) {
        purchaseService.delete(purchaseId);
        return new ResponseEntity<>("Purchase deleted successfully.", HttpStatus.ACCEPTED);
    }
}
