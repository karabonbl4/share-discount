package com.senla.service.impl;

import com.senla.model.User;
import com.senla.repository.CustomRepository;
import com.senla.repository.impl.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements CustomRepository<User> {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public User saveOrUpdate(User user) {
        return null;
    }

    @Override
    public void delete(User user) {

    }
}
