package com.folaukaveinga.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_NULL)
public class ApiSuccessResponse extends ApiResponse {

	private String status;
	private String token;
	
	public ApiSuccessResponse(String status) {
		this(status,null);
	}
	
	public ApiSuccessResponse(String status, String message) {
		this(status,null,message);
	}
	
	public ApiSuccessResponse(String status, String token, String message) {
		super(message);
		this.status = status;
		this.token = token;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "ApiSuccessResponse [status=" + status + ", token=" + token + "]";
	}
}
