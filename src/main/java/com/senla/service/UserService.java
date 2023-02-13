package com.senla.service;

import com.senla.model.dto.UserDto;

import java.util.List;

public interface UserService {
    void save(UserDto userDto);
    UserDto findById(Long id);
    List<UserDto> findAll();
    void delete(Long userId);
    void update(UserDto userDto);
}
