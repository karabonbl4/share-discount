package com.senla.service.impl;

import com.senla.repository.UserRepository;
import com.senla.model.User;
import com.senla.service.UserService;
import com.senla.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public void save(UserDto userDto) {
        User map = modelMapper.map(userDto, User.class);
        userRepository.save(map);
    }

    @Override
    public UserDto findById(Long id) {
        User byId = userRepository.findById(id);
        return modelMapper.map(byId, UserDto.class);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public void delete(UserDto userDto) {
        User map = modelMapper.map(userDto, User.class);
        userRepository.delete(map);
    }

    @Override
    public void update(UserDto userDto) {
        User map = modelMapper.map(userDto, User.class);
        userRepository.update(map);
    }
}
