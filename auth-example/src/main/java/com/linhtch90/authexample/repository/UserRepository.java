package com.linhtch90.authexample.repository;

import java.util.Optional;

import com.linhtch90.authexample.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    
}
