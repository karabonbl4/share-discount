package com.senla.repository;

import com.senla.model.User;

import java.util.List;

public interface UserRepository {
    void save(User t);
    User findById(Long id);
    List<User> findAll();
    void update (User t);
    void delete (User t);
}
