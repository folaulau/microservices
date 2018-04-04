package com.folaukaveinga.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.folaukaveinga.model.User;
import com.folaukaveinga.service.UserService;
/**
 * CustomUserDetailsService - Not being used
 * @author fkaveinga
 *
 */
public class CustomUserDetailsService implements UserDetailsService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Loading User by username: {}",username);
		User user = userService.getUserByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User not found by name: " + username);
		}
		log.info("User: {}",user);
		return generateUserDetails(user);

	}
	
	private UserDetails generateUserDetails(User user) {
		UserBuilder builder = null;
		builder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
		builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
		builder.roles((String[]) user.getRoles().toArray());
		return builder.build();
    }

}
