package com.hernan.gestionproductos.service.impl;

import com.hernan.gestionproductos.entity.UserEntity;
import com.hernan.gestionproductos.repository.UserRepository;
import com.hernan.gestionproductos.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserEntity createUser(String username, String password, String role) {
		if (userRepository.findByUsername(username).isPresent()) {
			throw new RuntimeException("El usuario ya existe");
		}

		UserEntity user = new UserEntity();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole(role);
		user.setActive(true);
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());
		return userRepository.save(user);
	}

	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}

	public UserEntity getUserByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	}
}
