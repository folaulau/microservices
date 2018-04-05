package com.folaukaveinga.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.folaukaveinga.model.User;
import com.folaukaveinga.service.UserService;
import com.folaukaveinga.utility.PasswordUtil;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("authenticate(...)");
		String username = authentication.getName();
	    String password = authentication.getCredentials().toString();
	    log.info("username: {}",username);
	    log.info("password: {}",password);
	    User user = userService.getUserByUsername(username);
	    
	    if(user==null) {
	    		throw new UsernameNotFoundException("User not found by name: " + username);
	    }
	    
	    if(PasswordUtil.verify(password, user.getPassword())) {
	    	
	    }
	    
		return new UsernamePasswordAuthenticationToken(user.getUsername(), password, generateAuthorities(user));
	}
	
	private List<GrantedAuthority> generateAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<>(user.getRoles().size());
		for (String role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		}
		return authorities;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
