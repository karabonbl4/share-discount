package com.senla.controller;

import com.senla.service.CouponService;
import com.senla.model.dto.CouponDto;
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
@RequestMapping(value = "/coupons")
public class CouponController {

    private final CouponService couponService;

    @GetMapping
    @Secured(value = "ROLE_ADMIN")
    public List<CouponDto> getAllCoupons() {
        return couponService.findAll();
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(value = "/{id}")
    public CouponDto getCouponById(@PathVariable(name = "id") Long couponId) {
        return couponService.findById(couponId);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(value = "/purchase/{id}")
    public CouponDto getCouponByPurchase(@PathVariable(name = "id") Long purchaseId) {
        return couponService.findByPurchaseId(purchaseId);
    }
    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping(value = "/create")
    public ResponseEntity<?> addCoupon(@RequestBody CouponDto newCoupon) {
        couponService.save(newCoupon);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(newCoupon.getName())
                .toUri());
        return new ResponseEntity<>(newCoupon, headers, HttpStatus.CREATED);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @PutMapping(value = "/update")
    public ResponseEntity<?> updateCoupon(@RequestBody CouponDto updateCoupon) {
        couponService.update(updateCoupon);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(updateCoupon.getName())
                .toUri());
        return new ResponseEntity<>(updateCoupon, headers, HttpStatus.ACCEPTED);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteCoupon(@PathVariable(name = "id") Long couponId) {
        couponService.delete(couponId);
        return new ResponseEntity<>("Coupon deleted successfully.", HttpStatus.ACCEPTED);
    }
}
