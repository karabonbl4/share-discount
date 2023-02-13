package com.senla.controller;

import com.senla.model.dto.DiscountPolicyDto;
import com.senla.service.DiscountPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/policies")
public class DiscountPolicyController {
    private final DiscountPolicyService policyService;

    @GetMapping
    public List<DiscountPolicyDto> getAllPolicies() {
        return policyService.findAll();
    }

    @GetMapping(value = "/{id}")
    public DiscountPolicyDto getPolicyById(@PathVariable(name = "id") Long policyId) {
        return policyService.findById(policyId);
    }


    @GetMapping(value = "/trademark/{id}")
    public List<DiscountPolicyDto> getPolicyByTrademarkId(@PathVariable(name = "id") Long trademarkId) {
        return policyService.findByTrademarkId(trademarkId);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createPolicy(@RequestBody DiscountPolicyDto newPolicy) {
        policyService.save(newPolicy);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(newPolicy.getTitle())
                .toUri());
        return new ResponseEntity<>(newPolicy, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updatePolicy(@RequestBody DiscountPolicyDto discountPolicyDto) {
        policyService.update(discountPolicyDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(discountPolicyDto.getTitle())
                .toUri());
        return new ResponseEntity<>(discountPolicyDto, headers, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?> deletePolicy(@PathVariable(name = "id") Long policyId) {
        policyService.delete(policyId);
        return new ResponseEntity<>("Policy deleted successfully.", HttpStatus.ACCEPTED);
    }
}
