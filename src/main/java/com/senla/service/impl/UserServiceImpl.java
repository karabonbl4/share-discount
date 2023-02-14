package com.senla.service.impl;

<<<<<<< HEAD
import com.senla.repository.UserRepository;
=======
import com.senla.annotation.Transaction;
import com.senla.model.User;
import com.senla.repository.impl.UserRepository;
>>>>>>> main
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

<<<<<<< HEAD
    @Override
    public UserDto save(UserDto userDto) {
        return null;
=======
    @Transaction
    @Override
    public UserDto save(UserDto userDto) {
        User map = modelMapper.map(userDto, User.class);
        User user = userRepository.saveOrUpdate(map);
        return modelMapper.map(user, UserDto.class);

>>>>>>> main
    }

    @Transaction
    @Override
    public UserDto findById(Long id) {
<<<<<<< HEAD
        return null;
=======
        User byId = userRepository.findById(id);
        return modelMapper.map(byId, UserDto.class);
>>>>>>> main
    }

    @Transaction
    @Override
    public List<UserDto> findAll() {
        return null;
    }

    @Transaction
    @Override
    public boolean delete(UserDto userDto) {
<<<<<<< HEAD
        return false;
    }

    @Override
    public UserDto update(UserDto userDto) {
        return null;
=======
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
>>>>>>> main
    }
}
