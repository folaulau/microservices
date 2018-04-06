package com.folaukaveinga.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.folaukaveinga.cache.UserCache;
import com.folaukaveinga.jwt.JwtTokenUtil;
import com.folaukaveinga.model.User;
import com.folaukaveinga.service.UserService;
import com.folaukaveinga.utility.ApiResponse;
import com.folaukaveinga.utility.ApiSuccessResponse;
import com.folaukaveinga.utility.FormatterUtil;

/**
 * CustomUsernamePassworAuthenticationFilter
 * @author fkaveinga
 *
 */
public class CustomUsernamePassworAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${restaurant.api-auth-token}")
	private String authToken;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserCache userCache;
	

	@Autowired
	private UserService userService;
	
	public CustomUsernamePassworAuthenticationFilter(String loginUrl, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(loginUrl));
		setAuthenticationManager(authManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		log.info("attemptAuthentication(...)");
		String username = getUsername(request);
		String password = getPassword(request);
		log.info("username: {}",username);
		log.info("password: {}",password);
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		log.info("successfulAuthentication(...)");
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.OK.value());
		
		String username = authResult.getPrincipal().toString();
		
		User user = userService.getUserByEmail(username);
		
		String jwtToken = jwtTokenUtil.generateToken(user);
		
		userCache.add(jwtToken, user);
		
		log.info("username: {}",authResult.getPrincipal().toString());
		log.info("jwtToken: {}",jwtToken);
		
		FormatterUtil.getObjectMapper().writeValue(response.getWriter(), new ApiSuccessResponse(ApiResponse.SUCCESS, jwtToken, null));
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		log.info("unsuccessfulAuthentication(...)");
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		
		String message = failed.getLocalizedMessage();
		log.info("Error message: {}",message);

		FormatterUtil.getObjectMapper().writeValue(response.getWriter(), new ApiSuccessResponse(ApiResponse.FAILURE,message));
	}
	
	private String getUsername(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("authorization");
		//log.info("getUsername(...), authorizationHeader: {}",authorizationHeader);

		String usernamePasswordToken = StringUtils.substringAfter(authorizationHeader, " ").trim();

		String rawToken = decodeUsernamePasswordToken(usernamePasswordToken);
		
		String username  = StringUtils.substringBefore(rawToken, ":");
		
		return username;
		
	}
	
	private String getPassword(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("authorization");
		//log.info("getPassword(...), authorizationHeader: {}",authorizationHeader);
		String usernamePasswordToken = StringUtils.substringAfter(authorizationHeader, " ").trim();

		String rawToken = decodeUsernamePasswordToken(usernamePasswordToken);
		
		
		String password  = StringUtils.substringAfter(rawToken, ":");
		
		return password;
		
	}
	
	
	
	private String decodeUsernamePasswordToken(String usernamePasswordToken) {
		byte[] decodedBytes = Base64.getDecoder().decode(usernamePasswordToken);
		return new String(decodedBytes);
	}
}
