package com.senla.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.service.UserService;
import com.senla.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public String findById(String id){
        long lId = Long.parseLong(id);
        UserDto byId = userService.findById(lId);
        return objectMapper.writeValueAsString(byId);
    }
}
