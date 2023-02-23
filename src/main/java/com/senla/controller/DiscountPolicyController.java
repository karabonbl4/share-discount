package com.senla.controller;

import com.senla.model.dto.DiscountPolicyDto;
import com.senla.service.DiscountPolicyService;
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
@RequestMapping(value = "/policies")
public class DiscountPolicyController {
    private final DiscountPolicyService policyService;

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping
    public List<DiscountPolicyDto> getAllPolicies() {
        return policyService.findAll();
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(value = "/{id}")
    public DiscountPolicyDto getPolicyById(@PathVariable(name = "id") Long policyId) {
        return policyService.findById(policyId);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(value = "/trademark/{id}")
    public List<DiscountPolicyDto> getPolicyByTrademarkId(@PathVariable(name = "id") Long trademarkId) {
        return policyService.findByTrademarkId(trademarkId);
    }

    @PostMapping
    public ResponseEntity<DiscountPolicyDto> createPolicy(@RequestBody DiscountPolicyDto newPolicy) {
        policyService.save(newPolicy);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(newPolicy.getTitle())
                .toUri());
        return new ResponseEntity<>(newPolicy, headers, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<DiscountPolicyDto> updatePolicy(@RequestBody DiscountPolicyDto discountPolicyDto) {
        policyService.update(discountPolicyDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(discountPolicyDto.getTitle())
                .toUri());
        return new ResponseEntity<>(discountPolicyDto, headers, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePolicy(@PathVariable(name = "id") Long policyId) {
        policyService.delete(policyId);
        return new ResponseEntity<>("Policy deleted successfully.", HttpStatus.ACCEPTED);
    }
}
