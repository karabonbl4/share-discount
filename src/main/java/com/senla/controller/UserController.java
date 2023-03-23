package com.senla.controller;

import com.senla.model.dto.UserDto;
import com.senla.model.dto.save.UserDtoForUpdate;
import com.senla.model.dto.save.UserPurchaseServiceDto;
import com.senla.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping
    public List<UserDto> getAllUsers(@Param(value = "pageNumber") Integer pageNumber,
                                     @Param(value = "pageSize") Integer pageSize) {
        return userService.findAll(pageNumber, pageSize);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping(value = "/{id}")
    public UserDto getById(@PathVariable(name = "id") Long userId) {
        return userService.findById(userId);
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto newUser) {
        userService.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @Secured(value = "ROLE_ADMIN")
    @PostMapping(value = "/purchase/service")
    public ResponseEntity<UserPurchaseServiceDto> createPurchaseService(@RequestBody UserPurchaseServiceDto newPurchaseService) {
        UserPurchaseServiceDto purchaseServiceAccount = userService.createPurchaseServiceAccount(newPurchaseService);
        return new ResponseEntity<>(purchaseServiceAccount, HttpStatus.CREATED);
    }

    @Secured(value = "ROLE_ADMIN")
    @PostMapping(value = "/setting")
    public ResponseEntity<String> confirmAdminTrademark(@RequestParam(value = "adminId") Long adminId,
                                                        @RequestParam(value = "trademarkId") Long trademarkId) {
        userService.setAdminToTrademark(adminId, trademarkId);
        return new ResponseEntity<>("Admin is set!", HttpStatus.OK);
    }

    @Secured(value = "ROLE_ADMIN")
    @PostMapping(value = "/removing")
    public ResponseEntity<String> removePurchaseService(@RequestParam(value = "adminId") Long adminId,
                                                        @RequestParam(value = "trademarkId") Long trademarkId) {
        userService.removeAdminTrademark(adminId, trademarkId);
        return new ResponseEntity<>("Admin is removed!", HttpStatus.OK);
    }

    @Secured(value = "ROLE_USER")
    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDtoForUpdate userUpd) {
        userService.update(userUpd);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(userUpd.getFirstName())
                .toUri());
        return new ResponseEntity<>(userUpd, headers, HttpStatus.ACCEPTED);
    }

    @Secured(value = "ROLE_USER")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long userId) {
        userService.delete(userId);
        return new ResponseEntity<>("User deleted successfully.", HttpStatus.ACCEPTED);
    }
}
