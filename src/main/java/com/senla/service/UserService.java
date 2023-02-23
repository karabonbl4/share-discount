package com.senla.service;

import com.senla.model.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto save(UserDto userDto);
    UserDto findById(Long id);
    List<UserDto> findAll();
    void delete(Long userId);
    UserDto update(UserDto userDto);
}
