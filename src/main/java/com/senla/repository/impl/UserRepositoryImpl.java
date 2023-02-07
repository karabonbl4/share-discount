package com.senla.repository.impl;

import com.senla.model.User;
import com.senla.repository.DefaultRepositoryImpl;
import com.senla.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends DefaultRepositoryImpl<User, Long> implements UserRepository {

}
