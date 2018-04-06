package com.folaukaveinga.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.folaukaveinga.security.CustomAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CustomAuthenticationFilter customAuthenticationFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
        		.antMatchers("/").permitAll()
        		.antMatchers(HttpMethod.POST, "/login").permitAll()
        		.anyRequest().authenticated();
		
		http.addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		// this disables session creation on Spring Security
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

}
