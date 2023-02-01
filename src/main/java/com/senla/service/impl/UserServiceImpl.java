package com.senla.service.impl;

import com.senla.model.User;
import com.senla.repository.impl.UserRepository;
import com.senla.service.UserService;
import com.senla.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public UserDto save(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User user1 = userRepository.saveOrUpdate(user);
        return modelMapper.map(user1, UserDto.class);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        userRepository.delete(user);
        return userRepository.findById(userDto.getId()) == null;
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        return modelMapper.map(userRepository.saveOrUpdate(user), UserDto.class);
    }
}
