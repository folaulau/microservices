package com.folaukaveinga.user;


import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.folaukaveinga.cache.UserCache;
import com.folaukaveinga.jwt.JwtTokenUtil;
import com.folaukaveinga.model.User;
import com.folaukaveinga.service.UserService;
import com.folaukaveinga.utility.PasswordUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserIntegrationTester {
	
	@Autowired
	private UserService userService;
	

	@Autowired
	private UserCache userCache;
	
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	

	@Test
	public void testVerifyCredentials() {
		String password = "test12";
		
		User user = userService.getUserByUsername("folau@gmail.com");
		
		assertTrue(PasswordUtil.verify(password,user.getPassword()));
	}
	
	@Test
	public void getUserByToken() {
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmb2xhdUBnbWFpbC5jb20iLCJyb2xlcyI6WyJ1c2VyIl0sImlkIjoxLCJleHAiOjE1MjI4MDY1MjUsImlhdCI6MTUyMjgwNTkyNSwiZW1haWwiOiJmb2xhdUBnbWFpbC5jb20ifQ.4K-U9awUGy9GvfYeBziagZUugM83XJ-xP-1-3u4LFsPxnkETPbJNXHuxLKfGy82Q4WhM3l7oORlSatYkVsv2GQ";
		User user = userCache.findUserByToken(token);
		System.out.println(user.toJson());
	}
	

	@Test
	public void getUserPayloadFromToken() {
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmb2xhdUBnbWFpbC5jb20iLCJyb2xlcyI6WyJ1c2VyIl0sImlkIjoxLCJleHAiOjE1MjI4MDU2NTYsImlhdCI6MTUyMjgwNTA1NiwiZW1haWwiOiJmb2xhdUBnbWFpbC5jb20ifQ.MXBOuqIfN26tAd3Bh9xoC0GqB95VmEjHJJ7L50CSdIPjJjqz3fi1v0o-xK2wYvE30jEwZrLs3B6SmyVpcCd7AA";
		User user = jwtTokenUtil.getUserFromToken(token);
		System.out.println(user.toJson());
		
	}
	
	
	



}
