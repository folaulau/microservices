package com.folaukaveinga.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.folaukaveinga.exception.ApiError;
import com.folaukaveinga.exception.GetException;
import com.folaukaveinga.model.User;
import com.folaukaveinga.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User save(User user) {
		return this.userRepository.saveAndFlush(user);
	}

	public Optional<User> getUserByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}

	public User getUserByEmail(String email) {
		Optional<User> optionalUser = this.userRepository.findByUsername(email);
		return optionalUser.orElseThrow(() -> new GetException(new ApiError(HttpStatus.NOT_FOUND, "User not found with username: " + email)));
	}
}
