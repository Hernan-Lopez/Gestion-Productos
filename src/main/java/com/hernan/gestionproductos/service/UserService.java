package com.hernan.gestionproductos.service;

import java.util.List;

import com.hernan.gestionproductos.entity.UserEntity;

public interface UserService {

	UserEntity createUser(String username, String password, String role);

	List<UserEntity> getAllUsers();

	UserEntity getUserByUsername(String username);

}
