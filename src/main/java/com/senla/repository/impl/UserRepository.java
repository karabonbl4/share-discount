package com.senla.repository.impl;

import com.senla.model.User;
import com.senla.repository.CustomRepository;
import com.senla.repository.DefaultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository implements CustomRepository<User> {
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
