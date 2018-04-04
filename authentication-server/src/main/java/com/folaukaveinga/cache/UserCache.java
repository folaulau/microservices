package com.folaukaveinga.cache;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.folaukaveinga.model.User;

@Service
public class UserCache {
	
	private static final String HASH_KEY = "restaurant-user";
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	// Use Hash to hash key and store that key instead of plain text key
	private HashOperations<String, Object, Object> hashOperations;

	@PostConstruct
	public void init() {
		this.hashOperations = this.redisTemplate.opsForHash();
	}

	public void add(String token, User user) {
		this.hashOperations.put(HASH_KEY, token, user);
	}

	public User findUserByToken(String token) {
		return (User) hashOperations.get(HASH_KEY, token);
	}

	public long delete(String token) {
		return hashOperations.delete(HASH_KEY, token);
	}
	
	public Map<Object, Object> findAllUsers() {
		return hashOperations.entries(HASH_KEY);
	}
}
