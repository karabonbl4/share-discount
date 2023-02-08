package com.senla.service.impl;

import com.senla.annotation.Transaction;
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

    @Transaction
    @Override
    public UserDto save(UserDto userDto) {
        User map = modelMapper.map(userDto, User.class);
        User user = userRepository.saveOrUpdate(map);
        return modelMapper.map(user, UserDto.class);

    }

    @Transaction
    @Override
    public UserDto findById(Long id) {
        User byId = userRepository.findById(id);
        return modelMapper.map(byId, UserDto.class);
    }

    @Transaction
    @Override
    public List<UserDto> findAll() {
        return null;
    }

    @Transaction
    @Override
    public boolean delete(UserDto userDto) {
        User map = modelMapper.map(userDto, User.class);
        userRepository.delete(map);
        if (userRepository.findById(userDto.getId()) == null) {
            return true;
        }
        return false;
    }

    @Transaction
    @Override
    public UserDto update(UserDto userDto) {
        User map = modelMapper.map(userDto, User.class);
        User user = userRepository.saveOrUpdate(map);
        return modelMapper.map(user, UserDto.class);
    }
}
