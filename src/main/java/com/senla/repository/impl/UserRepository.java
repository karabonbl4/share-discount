package com.senla.repository.impl;

import com.senla.model.User;
import com.senla.repository.DefaultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository extends DefaultRepository<User> {
}
