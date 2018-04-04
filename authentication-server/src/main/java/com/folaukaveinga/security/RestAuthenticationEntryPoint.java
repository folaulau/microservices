package com.folaukaveinga.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		log.info("RestAuthenticationEntryPoint : commence(...)");
		log.info("AuthenticationException, msg: {}",exception.getLocalizedMessage());
		response.sendError( HttpServletResponse.SC_UNAUTHORIZED, exception.getLocalizedMessage());
		
		log.info("username = {}",request.getParameter("username"));
		log.info("passsword = {}",request.getParameter("passsword"));
	}
	
	
	
}
