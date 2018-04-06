package com.folaukaveinga.rest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.folaukaveinga.cache.UserCache;
import com.folaukaveinga.exception.ApiError;
import com.folaukaveinga.exception.ProcessException;
import com.folaukaveinga.utility.ApiResponse;
import com.folaukaveinga.utility.ApiSuccessResponse;

@RestController
public class LogoutController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserCache userCache;
	
	@Value("${restaurant.api-auth-token-header}")
	private String restaurantHeaderToken;
	
	@DeleteMapping("/api/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		String authToken = request.getHeader(restaurantHeaderToken);
		
		if(authToken==null) {
			throw new ProcessException(new ApiError(HttpStatus.BAD_REQUEST, "Header "+restaurantHeaderToken+" is missing"));
		}
		
		log.info("User logging out...");
		log.info("Auth Token: {}",authToken);
		long num = userCache.delete(authToken);
		log.info("User logged out. {}",num);
		
		return new ResponseEntity<>(new ApiSuccessResponse(ApiResponse.SUCCESS), HttpStatus.OK);
	}
}
