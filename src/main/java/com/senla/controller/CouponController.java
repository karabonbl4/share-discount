package com.senla.controller;

import com.senla.service.CouponService;
import com.senla.model.dto.CouponDto;
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
@RequestMapping(value = "/coupons")
public class CouponController {

    private final CouponService couponService;

    @GetMapping(value = "/sort")
    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    public List<CouponDto> getAllCouponsBySort(@Param("pageNumber") Integer pageNumber,
                                               @Param("pageSize") Integer pageSize,
                                               @Param("sortingBy") String[] sortingBy) {
        return couponService.findAllWithSort(pageNumber, pageSize, sortingBy);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(value = "/{id}")
    public CouponDto getCouponById(@PathVariable(name = "id") Long couponId) {
        return couponService.findById(couponId);
    }

    @Secured(value = "ROLE_USER")
    @GetMapping(value = "/my")
    public List<CouponDto> getCouponByUser(@Param("pageNumber") Integer pageNumber,
                                           @Param("pageSize") Integer pageSize,
                                           @Param("sortingBy") String[] sortingBy) {
        return couponService.findByUser(pageNumber, pageSize, sortingBy);
    }

    @Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping(value = "/purchase/{id}")
    public CouponDto getCouponByPurchase(@PathVariable(name = "id") Long purchaseId) {
        return couponService.findByPurchaseId(purchaseId);
    }

    @Secured(value = "ROLE_USER")
    @PostMapping
    public ResponseEntity<CouponDto> addCoupon(@RequestBody CouponDto newCoupon) {
        couponService.save(newCoupon);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(newCoupon.getName())
                .toUri());
        return new ResponseEntity<>(newCoupon, headers, HttpStatus.CREATED);
    }

    @Secured(value = {"ROLE_USER"})
    @PutMapping(value = "/{id}/reservation")
    public ResponseEntity<String> reserveCoupon(@PathVariable(name = "id") Long couponId) {
        couponService.reserve(couponId);
        return new ResponseEntity<>("Coupon is reserved successfully!", HttpStatus.ACCEPTED);
    }

    @Secured(value = "ROLE_USER")
    @PutMapping
    public ResponseEntity<CouponDto> updateCoupon(@RequestBody CouponDto updateCoupon) {
        couponService.update(updateCoupon);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(updateCoupon.getName())
                .toUri());
        return new ResponseEntity<>(updateCoupon, headers, HttpStatus.ACCEPTED);
    }

    @Secured(value = "ROLE_ADMIN")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCoupon(@PathVariable(name = "id") Long couponId) {
        couponService.delete(couponId);
        return new ResponseEntity<>("Coupon is deleted successfully.", HttpStatus.ACCEPTED);
    }

    @Secured(value = "ROLE_USER")
    @PutMapping(value = "/deactivation/{id}")
    public ResponseEntity<String> deactivateCoupon(@PathVariable(name = "id") Long couponId) {
        couponService.deactivate(couponId);
        return new ResponseEntity<>("Coupon is deactivated successfully.", HttpStatus.ACCEPTED);
    }
}
