package com.senla.controller;

import com.senla.model.dto.UserDto;
import com.senla.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping(value = "/{id}")
    public UserDto getById(@PathVariable(name = "id") Long userId) {
        return userService.findById(userId);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody UserDto newUser) {
        userService.save(newUser);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(newUser.getFirstName())
                .toUri());
        return new ResponseEntity<>(newUser, headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userUpd) {
        userService.update(userUpd);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
                .buildAndExpand(userUpd.getFirstName())
                .toUri());
        return new ResponseEntity<>(userUpd, headers, HttpStatus.ACCEPTED);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long userId){
        userService.delete(userId);
        return new ResponseEntity<>("User deleted successfully.", HttpStatus.ACCEPTED);
    }
}
