package com.linhtch90.authexample.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.linhtch90.authexample.entity.User;
import com.linhtch90.authexample.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptEncoder;

    @Override
    public Integer saveUser(User user) {
        user.setPassword(bCryptEncoder.encode(user.getPassword()));
        return userRepo.save(user).getId();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = userRepo.findByUsername(username);
        org.springframework.security.core.userdetails.User springUser = null;

        if (opt.isEmpty()) {
            throw new UsernameNotFoundException("Username: " + username + " not found...");
        } else {
            User user = opt.get();
            Set<String> roles = user.getRoles();
            Set<GrantedAuthority> ga = new HashSet<>();
            for (String role : roles) {
                ga.add(new SimpleGrantedAuthority(role));
            }
            springUser = new org.springframework.security.core.userdetails.User(username, user.getPassword(), ga);
            return springUser;
        }
    }
}
