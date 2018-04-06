package com.folaukaveinga.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
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

/**
 * CustomAuthenticationProvider
 * @author fkaveinga
 *
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	/**
	 * Authenticate user by credentials
	 * @author fkaveinga
	 * @return Authentication
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("authenticate(...)");
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		log.info("username: {}", username);
		log.info("password: {}", password);
		Optional<User> optUser = userService.getUserByUsername(username);

		if (!optUser.isPresent()) {
			throw new UsernameNotFoundException("Username or password is invalid");
		}

		User user = optUser.get();

		if (user.getPassword()==null || !PasswordUtil.verify(password, user.getPassword())) {
			throw new BadCredentialsException("Username or password is invalid");
		}

		return new UsernamePasswordAuthenticationToken(user.getUsername(), password, generateAuthorities(user));
	}

	/**
	 * Get Authorities for User
	 * @param user
	 * @return List<GrantedAuthority>
	 */
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
