package com.folaukaveinga.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.folaukaveinga.cache.UserCache;
import com.folaukaveinga.exception.ApiError;
import com.folaukaveinga.jwt.JwtPayload;
import com.folaukaveinga.jwt.JwtTokenUtil;
import com.folaukaveinga.utility.ApiSession;
import com.folaukaveinga.utility.FormatterUtil;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserCache userCache;

	/**
	 * Handle token missing error <br>
	 * Handle cached user not found error <br>
	 * Handle user with no roles <br>
	 * If all goes well, let request continue down the line
	 * 
	 * @author fkaveinga
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("doFilterInternal(...)");
		String token = request.getHeader("token");
		log.info("Token: {}", token);

		if (token == null || token.isEmpty()) {
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setStatus(HttpStatus.BAD_REQUEST.value());

			String message = "Missing token in header";
			log.info("Error message: {}", message);

			FormatterUtil.getObjectMapper().writeValue(response.getWriter(), new ApiError(HttpStatus.BAD_REQUEST, message));
			return;
		}

		ApiSession apiSession = userCache.findApiSessionToken(token);
		if (apiSession == null) {
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setStatus(HttpStatus.BAD_REQUEST.value());

			String message = "Unauthorized request";
			log.info("Error message: {}", message);

			FormatterUtil.getObjectMapper().writeValue(response.getWriter(), new ApiError(HttpStatus.BAD_REQUEST, message));
			return;
		}
		JwtPayload payload = jwtTokenUtil.getPlayloadByToken(token);
		log.info(apiSession.toJson());
		
		// check token lifetime and extend it if needed.

		Authentication authentication = getAuthentication(request,payload);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, JwtPayload payload) {
		return new UsernamePasswordAuthenticationToken(payload.getEmail(), null, generateAuthorities(payload.getRoles()));
	}

	private List<GrantedAuthority> generateAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>(roles.size());
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
		}
		return authorities;
	}
}
