package com.senla.service.impl;

import com.senla.model.User;
import com.senla.repository.impl.UserRepository;
import com.senla.service.UserService;
import com.senla.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public UserDto save(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto findById(Long id) {
        User byId = userRepository.findById(id);
        return modelMapper.map(byId, UserDto.class);
    }

    @Override
    public List<UserDto> findAll() {
        return null;
    }

    @Override
    public boolean delete(UserDto userDto) {
        return false;
    }

    @Override
    public UserDto update(UserDto userDto) {
        return null;
    }
}
