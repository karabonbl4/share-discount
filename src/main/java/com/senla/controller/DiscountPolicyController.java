package com.senla.controller;

import com.senla.model.dto.DiscountPolicyDto;
import com.senla.model.dto.save.DiscountPolicyForSave;
import com.senla.service.DiscountPolicyService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(value = "/policies")
public class DiscountPolicyController {
    private final DiscountPolicyService policyService;

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping
    public List<DiscountPolicyDto> getAllPolicies(@Param(value = "pageNumber") Integer pageNumber,
                                                  @Param(value = "pageSize") Integer pageSize) {
        return policyService.findAll(pageNumber, pageSize);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(value = "/{id}")
    public DiscountPolicyDto getPolicyById(@PathVariable(name = "id") Long policyId) {
        return policyService.findById(policyId);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(value = "/trademark/{id}")
    public List<DiscountPolicyDto> getPolicyByTrademarkId(@PathVariable(name = "id") Long trademarkId,
                                                          @Param(value = "pageNumber") Integer pageNumber,
                                                          @Param(value = "pageSize") Integer pageSize) {
        return policyService.findByTrademarkId(trademarkId, pageNumber, pageSize);
    }

    @PostMapping
    public ResponseEntity<DiscountPolicyDto> createPolicy(@RequestBody DiscountPolicyForSave newPolicy) {
        DiscountPolicyDto savedPolicy = policyService.save(newPolicy);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(newPolicy.getTitle())
                .toUri());
        return new ResponseEntity<>(savedPolicy, headers, HttpStatus.CREATED);
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

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePolicy(@PathVariable(name = "id") Long policyId) {
        policyService.delete(policyId);
        return new ResponseEntity<>("Policy deleted successfully.", HttpStatus.ACCEPTED);
    }
}
