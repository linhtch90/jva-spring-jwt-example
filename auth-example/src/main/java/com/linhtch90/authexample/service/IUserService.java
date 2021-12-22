package com.linhtch90.authexample.service;

import java.util.Optional;

import com.linhtch90.authexample.entity.User;

public interface IUserService {
    Integer saveUser(User user);
    Optional<User> findByUsername(String username);
}
