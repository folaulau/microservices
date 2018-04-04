package com.folaukaveinga.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.folaukaveinga.cache.UserCache;
import com.folaukaveinga.jwt.JwtTokenUtil;
import com.folaukaveinga.model.User;
import com.folaukaveinga.service.UserService;
import com.folaukaveinga.utility.FormatterUtil;
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserCache userCache;
	

	@Autowired
	private UserService userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.info("onAuthenticationSuccess(...)");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.OK.value());
		String username = authentication.getPrincipal().toString();
		
		User user = userService.getUserByUsername(username);
		
		String jwtToken = jwtTokenUtil.generateToken(user);
		
		userCache.add(jwtToken, user);
		
		log.info("username: {}",authentication.getPrincipal().toString());
		log.info("jwtToken: {}",jwtToken);
		Map<String, String> tokenMap = new HashMap<String, String>();
		
		
		tokenMap.put("token", jwtToken);

		FormatterUtil.getObjectMapper().writeValue(response.getWriter(), tokenMap);
	}

}
