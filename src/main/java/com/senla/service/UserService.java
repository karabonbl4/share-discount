package com.senla.service;

import com.senla.service.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto save(UserDto userDto);
    UserDto findById(Long id);
    List<UserDto> findAll();
    boolean delete(UserDto userDto);
    UserDto update(UserDto userDto);
}
