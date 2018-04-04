package com.folaukaveinga.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folaukaveinga.model.User;
import com.folaukaveinga.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User save(User user) {
		return this.userRepository.save(user);
	}
	
	public User getUserByUsername(String username) {
		Optional<User> optUser = this.userRepository.findByUsername(username);
		return optUser.orElseThrow(()->{return new RuntimeException("Error finding user with username: "+username);});
	}
}
