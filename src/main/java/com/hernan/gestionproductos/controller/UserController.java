package com.hernan.gestionproductos.controller;

import com.hernan.gestionproductos.entity.UserEntity;
import com.hernan.gestionproductos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserEntity registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        return userService.createUser(username, password, role);
    }

    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }
}