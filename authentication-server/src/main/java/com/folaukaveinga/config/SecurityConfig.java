package com.folaukaveinga.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.folaukaveinga.security.CustomAuthenticationProvider;
import com.folaukaveinga.security.CustomUsernamePassworAuthenticationFilter;
import com.folaukaveinga.utility.PathConstants;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	
	@Bean
	public CustomUsernamePassworAuthenticationFilter customUsernamePassworAuthenticationFilter() throws Exception {
		return new CustomUsernamePassworAuthenticationFilter(PathConstants.LOGIN_URL,authenticationManagerBean());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors().and().csrf().disable()
			.authorizeRequests()
				.antMatchers(HttpMethod.DELETE, PathConstants.LOGOUT_URL).permitAll()
				.antMatchers(HttpMethod.POST, PathConstants.LOGIN_URL).permitAll()
				.anyRequest().denyAll()
				.and()
					.addFilterBefore(customUsernamePassworAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class)
				// this disables session creation on Spring Security
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.authenticationProvider(customAuthenticationProvider);

		//builder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}



	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}
