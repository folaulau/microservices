package com.folaukaveinga.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.folaukaveinga.security.CustomAuthenticationFailureHandler;
import com.folaukaveinga.security.CustomAuthenticationProvider;
import com.folaukaveinga.security.CustomAuthenticationSuccessHandler;
import com.folaukaveinga.security.CustomUserDetailsService;
import com.folaukaveinga.security.CustomUsernamePassworAuthenticationFilter;
import com.folaukaveinga.security.RestAuthenticationEntryPoint;
import com.folaukaveinga.utility.PathConstants;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
//
//	@Autowired
//	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
//
//	@Autowired
//	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

//	@Autowired
//	private CustomUserDetailsService customUserDetailsService;
	
	@Bean
	public CustomUsernamePassworAuthenticationFilter customUsernamePassworAuthenticationFilter() throws Exception {
		return new CustomUsernamePassworAuthenticationFilter(PathConstants.LOGIN_URL,authenticationManagerBean());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.cors().and().csrf().disable()
			.authorizeRequests()
				.antMatchers(HttpMethod.DELETE, PathConstants.LOGOUT_URL).permitAll()
				.antMatchers(HttpMethod.POST, PathConstants.LOGIN_URL).permitAll()
				.anyRequest().authenticated()
				.and()
					.addFilterBefore(customUsernamePassworAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class)
				// this disables session creation on Spring Security
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		/*
		 * http.cors().and().csrf().disable() .authorizeRequests()
		 * .antMatchers(HttpMethod.POST, "/api/login").permitAll()
		 * .anyRequest().authenticated() .and() .httpBasic() .and()
		 * .formLogin().usernameParameter("username").passwordParameter("password")
		 * .and()
		 * .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		 * 
		 * http .cors() .disable() .csrf() .disable() .authorizeRequests()
		 * .anyRequest().authenticated() .and() .httpBasic()
		 * .authenticationEntryPoint(restAuthenticationEntryPoint) .and() .formLogin()
		 * .successHandler(customAuthenticationSuccessHandler)
		 * .failureHandler(customAuthenticationFailureHandler);
		 * 
		 * http .cors() .disable() .csrf() .disable() .exceptionHandling() .and()
		 * .authorizeRequests() .anyRequest().authenticated() .and() .formLogin()
		 * .loginPage("/api/login").permitAll() .usernameParameter("username")
		 * .passwordParameter("password")
		 * .successHandler(customAuthenticationSuccessHandler)
		 * .failureHandler(customAuthenticationFailureHandler);
		 * 
		 * http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.
		 * STATELESS);
		 * 
		 */
		// @formatter:on
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

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
}
