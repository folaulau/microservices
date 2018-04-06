package com.folaukaveinga.user;


import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.folaukaveinga.cache.UserCache;
import com.folaukaveinga.jwt.JwtPayload;
import com.folaukaveinga.jwt.JwtTokenUtil;
import com.folaukaveinga.model.User;
import com.folaukaveinga.service.UserService;
import com.folaukaveinga.utility.ApiSession;
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
		
		User user = userService.getUserByEmail("folau@gmail.com");
		
		assertTrue(PasswordUtil.verify(password,user.getPassword()));
	}
	
	@Test 
	public void getUserByTokenFromCache() {
		String token = "eyJhbGciOiJIUzUxMiJ9.ewogICJpc3MiIDogImlhdGUtcmVzdGF1cmFudCIsCiAgInN1YiIgOiAicmV0YXVyYW50IiwKICAiZW1haWwiIDogImZvbGF1QGdtYWlsLmNvbSIsCiAgImZpcnN0TmFtZSIgOiAiTktuaHl5aFkiLAogICJsYXN0TmFtZSIgOiAiZm50aEJBSmkiLAogICJyb2xlcyIgOiBbICJ1c2VyIiBdLAogICJpYXQiIDogMTUyMjk3NzUyMTkyNSwKICAiZXhwIiA6IDE1MjI5OTkxMjE5MjUKfQ.6vqhQm7CUvAmVhB5f94dismBG3roj8I3LP6fKfKVRLKS1lEF5CmQlzlGE-upvfEJaXNj9gjveHD7XARqj-vXvA";
		ApiSession apiSession = userCache.findApiSessionToken(token);
		System.out.println(apiSession.toJson());
	}
	

	@Test
	public void getUserFromToken() {
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmb2xhdUBnbWFpbC5jb20iLCJyb2xlcyI6WyJ1c2VyIl0sImlkIjoxLCJleHAiOjE1MjI4MDU2NTYsImlhdCI6MTUyMjgwNTA1NiwiZW1haWwiOiJmb2xhdUBnbWFpbC5jb20ifQ.MXBOuqIfN26tAd3Bh9xoC0GqB95VmEjHJJ7L50CSdIPjJjqz3fi1v0o-xK2wYvE30jEwZrLs3B6SmyVpcCd7AA";
		User user = jwtTokenUtil.getUserFromToken(token);
		System.out.println(user.toJson());
		
	}
	
	@Test
	public void getUserPayloadFromToken() {
		String token = "eyJhbGciOiJIUzUxMiJ9.ewogICJpc3MiIDogImlhdGUtcmVzdGF1cmFudCIsCiAgInN1YiIgOiAxLAogICJlbWFpbCIgOiAiZm9sYXVAZ21haWwuY29tIiwKICAiZmlyc3ROYW1lIiA6ICJ0UG9QZk9LWiIsCiAgImxhc3ROYW1lIiA6ICJVRU9EVlRPVSIsCiAgInJvbGVzIiA6IFsgInVzZXIiIF0sCiAgImlhdCIgOiAxNTIzMDAxODMwODQzLAogICJleHAiIDogMTUyMzAyMzQzMDg0MAp9.yj2puT4cIlZoL-Vigrawk7vMr_x_O7Ea1URnJB14Gjxdxyw3i2bfF3tGMeuVdIvxFcFIFXxFD3blyNPQ7bWO6Q";
		JwtPayload payload = jwtTokenUtil.getPlayloadByToken(token);
		System.out.println(payload.toJson());
		
	}
	
	



}
