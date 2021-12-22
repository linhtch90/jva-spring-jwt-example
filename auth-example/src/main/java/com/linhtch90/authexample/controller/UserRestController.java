package com.linhtch90.authexample.controller;

import java.security.Principal;

import com.linhtch90.authexample.entity.User;
import com.linhtch90.authexample.entity.UserRequest;
import com.linhtch90.authexample.entity.UserResponse;
import com.linhtch90.authexample.jwt.JWTUtil;
import com.linhtch90.authexample.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserRestController {
    @Autowired
    private IUserService userService;

    @Autowired
    private JWTUtil util;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/saveuser")
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        Integer id = userService.saveUser(user);
        String message = "User with id = " + id + " saved successfully";
        return ResponseEntity.ok(message);
    }

    @PostMapping("/loginuser")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String token = util.generateToken(request.getUsername());
        return ResponseEntity.ok(new UserResponse(token, "Token generated successfully"));
    }

    @PostMapping("/getdata")
    public ResponseEntity<String> testAfterLogin(Principal p) {
        return ResponseEntity.ok("Welcome. You are: " + p.getName());
    }

    @PostMapping("/getdataadmin")
    public ResponseEntity<String> testAdminAfterLogin(Principal p) {
        return ResponseEntity.ok("Welcome to Admin page. You are: " + p.getName());
    }
}
