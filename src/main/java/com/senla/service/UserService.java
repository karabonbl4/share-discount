package com.senla.service;

import com.senla.service.dto.UserDto;

import java.util.List;

public interface UserService {
    void save(UserDto userDto);
    UserDto findById(Long id);
    List<UserDto> findAll();
    void delete(UserDto userDto);
    void update(UserDto userDto);
}
