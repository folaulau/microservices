package com.folaukaveinga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.folaukaveinga.model.User;
import java.lang.String;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
	User getByUsername(String username);
}
