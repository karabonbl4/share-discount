package com.senla.service.impl;

import com.senla.dao.RoleRepository;
import com.senla.dao.UserRepository;
import com.senla.exceptions.EntityNotFoundException;
import com.senla.model.entity.Role;
import com.senla.model.entity.User;
import com.senla.service.UserService;
import com.senla.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            return null;
        }
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(roleRepository.save(new Role("ROLE_USER"))));
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public UserDto findById(Long id) throws EntityNotFoundException {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long userId) {
        User user = userRepository.getReferenceById(userId);
        userRepository.delete(user);
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User returnedUserAfterUpdating = userRepository.saveAndFlush(user);
        return modelMapper.map(returnedUserAfterUpdating, UserDto.class);
    }
}
